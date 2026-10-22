package com.godoftime.client;

import com.godoftime.ability.TimeAbility;
import com.godoftime.ability.TimeMode;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHudState {

    private static TimeMode mode = TimeMode.YELLOW;
    private static TimeAbility ability = TimeAbility.ASTRAL_SLASH;

    public static void update(int modeId, int abilityId) {
        mode = TimeMode.byId(modeId);
        ability = TimeAbility.byId(abilityId);
    }

    public static TimeMode getMode() {
        return mode;
    }

    public static TimeAbility getAbility() {
        return ability;
    }
}
