package com.godoftime.client;

import com.godoftime.GodOfTimeMod;
import com.godoftime.ability.TimeAbility;
import com.godoftime.ability.TimeMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ModHudOverlay implements IGuiOverlay {

    public static final ModHudOverlay INSTANCE = new ModHudOverlay();

    @Override
    public void render(net.minecraftforge.client.gui.overlay.ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        TimeMode mode = ClientHudState.getMode();
        TimeAbility ability = ClientHudState.getAbility();

        String line1 = mode.getDisplayName();
        String line2 = ability.getDisplayName() + "  [R]";

        int color = switch (mode) {
            case YELLOW -> 0xFFFFD400;
            case GREEN -> 0xFF55FF55;
            case BLUE -> 0xFF55D5FF;
        };

        int x = 8;
        int y = screenHeight - 40;
        graphics.drawString(gui.getFont(), line1, x, y, color, true);
        graphics.drawString(gui.getFont(), line2, x, y + 10, 0xFFFFFFFF, true);
    }
}
