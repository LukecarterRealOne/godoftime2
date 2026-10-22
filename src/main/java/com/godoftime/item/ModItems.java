package com.godoftime.item;

import com.godoftime.GodOfTimeMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GodOfTimeMod.MOD_ID);

    public static final RegistryObject<Item> ASTRAL_SWORD = ITEMS.register("astral_sword",
            () -> new AstralSwordItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHRONO_CORE = ITEMS.register("chrono_core",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(net.minecraft.world.item.Rarity.EPIC)));

    public static void addToCreativeTab(CreativeModeTab.Output output) {
        output.accept(ASTRAL_SWORD.get());
        output.accept(CHRONO_CORE.get());
    }
}
