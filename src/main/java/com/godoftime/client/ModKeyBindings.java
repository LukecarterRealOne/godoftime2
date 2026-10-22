package com.godoftime.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class ModKeyBindings {

    public static final String CATEGORY = "key.categories.godoftime";

    public static final KeyMapping SWITCH_MODE = new KeyMapping(
            "key.godoftime.switch_mode", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            InputConstants.KEY_M, CATEGORY);

    public static final KeyMapping USE_ABILITY = new KeyMapping(
            "key.godoftime.use_ability", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            InputConstants.KEY_R, CATEGORY);

    public static final KeyMapping CYCLE_NEXT = new KeyMapping(
            "key.godoftime.cycle_next", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            InputConstants.KEY_RBRACKET, CATEGORY);

    public static final KeyMapping CYCLE_PREV = new KeyMapping(
            "key.godoftime.cycle_prev", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
            InputConstants.KEY_LBRACKET, CATEGORY);
}
