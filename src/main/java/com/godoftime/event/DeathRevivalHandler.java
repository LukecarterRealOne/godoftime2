package com.godoftime.event;

import com.godoftime.GodOfTimeMod;
import com.godoftime.capability.CapabilityEvents;
import com.godoftime.capability.ITimeGodData;
import com.godoftime.item.ModItems;
import com.godoftime.network.NetworkHandler;
import com.godoftime.network.S2CAscensionPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GodOfTimeMod.MOD_ID)
public class DeathRevivalHandler {

    private static final int ASCENSION_DELAY_TICKS = 50; // 2.5s of "death" before the revival completes

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ITimeGodData data = CapabilityEvents.get(player);

        event.setCanceled(true);
        player.setHealth(0.001F);
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, ASCENSION_DELAY_TICKS, 0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ASCENSION_DELAY_TICKS, 10, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ASCENSION_DELAY_TICKS + 20, 4, false, false));

        data.setAscensionTimer(ASCENSION_DELAY_TICKS);
        NetworkHandler.CHANNEL.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),
                new S2CAscensionPacket());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        ITimeGodData data = CapabilityEvents.get(player);

        if (data.getAscensionTimer() > 0) {
            int t = data.getAscensionTimer() - 1;
            data.setAscensionTimer(t);
            if (t == 0) {
                completeAscension(player, data);
            }
        }

        data.tickCooldowns();
        BladeAuraTicker.tick(player, data);
    }

    private static void completeAscension(ServerPlayer player, ITimeGodData data) {
        player.setHealth(player.getMaxHealth());
        player.getFoodData().setFoodLevel(20);
        player.removeAllEffects();

        if (!data.hasAstralSword()) {
            ItemStack sword = new ItemStack(ModItems.ASTRAL_SWORD.get());
            if (!player.getInventory().add(sword)) {
                player.drop(sword, false);
            }
            data.setHasAstralSword(true);
        }
        data.setAscended(true);
        data.setBladeAuraTicks(20 * 15); // 15 seconds of guardian blades on revival

        player.sendSystemMessage(Component.literal(
                "You have returned. The Astral Sword is yours — and the blades obey you now."));
    }
}
