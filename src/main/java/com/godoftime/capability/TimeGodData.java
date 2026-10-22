package com.godoftime.capability;

import com.godoftime.ability.TimeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class TimeGodData implements ITimeGodData {

    private TimeMode mode = TimeMode.YELLOW;
    private final int[] selectedAbility = new int[]{0, 17, 34}; // default first ability per mode
    private final Map<Integer, Integer> cooldowns = new HashMap<>();

    private boolean ascended = false;
    private boolean hasAstralSword = false;
    private int bladeAuraTicks = 0;

    private boolean hasCheckpoint = false;
    private double cpX, cpY, cpZ;

    private boolean hasAnchor = false;
    private double anchorX, anchorY, anchorZ;
    private int anchorDim = 0;

    private boolean secondChanceArmed = false;
    private int ascensionTimer = 0;

    @Override
    public int getAscensionTimer() {
        return ascensionTimer;
    }

    @Override
    public void setAscensionTimer(int ticks) {
        this.ascensionTimer = ticks;
    }

    private BlockPos lastBrokenPos = null;
    private BlockState lastBrokenState = Blocks.AIR.defaultBlockState();

    @Override
    public TimeMode getMode() {
        return mode;
    }

    @Override
    public void setMode(TimeMode mode) {
        this.mode = mode;
    }

    @Override
    public int getSelectedAbility(TimeMode mode) {
        return selectedAbility[mode.getId()];
    }

    @Override
    public void setSelectedAbility(TimeMode mode, int abilityId) {
        selectedAbility[mode.getId()] = abilityId;
    }

    @Override
    public Map<Integer, Integer> getCooldowns() {
        return cooldowns;
    }

    @Override
    public int getCooldown(int abilityId) {
        return cooldowns.getOrDefault(abilityId, 0);
    }

    @Override
    public void setCooldown(int abilityId, int ticks) {
        cooldowns.put(abilityId, ticks);
    }

    @Override
    public void tickCooldowns() {
        cooldowns.replaceAll((id, v) -> Math.max(0, v - 1));
        if (bladeAuraTicks > 0) bladeAuraTicks--;
    }

    @Override
    public boolean hasAscended() {
        return ascended;
    }

    @Override
    public void setAscended(boolean ascended) {
        this.ascended = ascended;
    }

    @Override
    public boolean hasAstralSword() {
        return hasAstralSword;
    }

    @Override
    public void setHasAstralSword(boolean has) {
        this.hasAstralSword = has;
    }

    @Override
    public int getBladeAuraTicks() {
        return bladeAuraTicks;
    }

    @Override
    public void setBladeAuraTicks(int ticks) {
        this.bladeAuraTicks = ticks;
    }

    @Override
    public double getCheckpointX() {
        return cpX;
    }

    @Override
    public double getCheckpointY() {
        return cpY;
    }

    @Override
    public double getCheckpointZ() {
        return cpZ;
    }

    @Override
    public boolean hasCheckpoint() {
        return hasCheckpoint;
    }

    @Override
    public void setCheckpoint(double x, double y, double z) {
        this.cpX = x;
        this.cpY = y;
        this.cpZ = z;
        this.hasCheckpoint = true;
    }

    @Override
    public boolean hasAnchor() {
        return hasAnchor;
    }

    @Override
    public void setAnchor(double x, double y, double z, int dimensionId) {
        this.anchorX = x;
        this.anchorY = y;
        this.anchorZ = z;
        this.anchorDim = dimensionId;
        this.hasAnchor = true;
    }

    @Override
    public void clearAnchor() {
        this.hasAnchor = false;
    }

    @Override
    public double getAnchorX() {
        return anchorX;
    }

    @Override
    public double getAnchorY() {
        return anchorY;
    }

    @Override
    public double getAnchorZ() {
        return anchorZ;
    }

    @Override
    public int getAnchorDimension() {
        return anchorDim;
    }

    @Override
    public boolean isSecondChanceArmed() {
        return secondChanceArmed;
    }

    @Override
    public void setSecondChanceArmed(boolean armed) {
        this.secondChanceArmed = armed;
    }

    @Override
    public void setLastBrokenBlock(BlockPos pos, BlockState state) {
        this.lastBrokenPos = pos;
        this.lastBrokenState = state;
    }

    @Override
    public BlockPos getLastBrokenPos() {
        return lastBrokenPos;
    }

    @Override
    public BlockState getLastBrokenState() {
        return lastBrokenState;
    }

    @Override
    public void copyFrom(ITimeGodData other) {
        this.mode = other.getMode();
        this.ascended = other.hasAscended();
        this.hasAstralSword = other.hasAstralSword();
        for (TimeMode m : TimeMode.values()) {
            this.selectedAbility[m.getId()] = other.getSelectedAbility(m);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mode", mode.getId());
        tag.putBoolean("ascended", ascended);
        tag.putBoolean("hasSword", hasAstralSword);
        tag.putIntArray("selected", selectedAbility);
        tag.putBoolean("hasAnchor", hasAnchor);
        tag.putDouble("anchorX", anchorX);
        tag.putDouble("anchorY", anchorY);
        tag.putDouble("anchorZ", anchorZ);
        tag.putInt("anchorDim", anchorDim);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        mode = TimeMode.byId(tag.getInt("mode"));
        ascended = tag.getBoolean("ascended");
        hasAstralSword = tag.getBoolean("hasSword");
        if (tag.contains("selected")) {
            int[] arr = tag.getIntArray("selected");
            for (int i = 0; i < Math.min(arr.length, selectedAbility.length); i++) {
                selectedAbility[i] = arr[i];
            }
        }
        hasAnchor = tag.getBoolean("hasAnchor");
        anchorX = tag.getDouble("anchorX");
        anchorY = tag.getDouble("anchorY");
        anchorZ = tag.getDouble("anchorZ");
        anchorDim = tag.getInt("anchorDim");
    }
}
