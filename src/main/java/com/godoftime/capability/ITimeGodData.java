package com.godoftime.capability;

import com.godoftime.ability.TimeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;

public interface ITimeGodData {

    TimeMode getMode();
    void setMode(TimeMode mode);

    int getSelectedAbility(TimeMode mode);
    void setSelectedAbility(TimeMode mode, int abilityId);

    Map<Integer, Integer> getCooldowns();
    int getCooldown(int abilityId);
    void setCooldown(int abilityId, int ticks);
    void tickCooldowns();

    boolean hasAscended();
    void setAscended(boolean ascended);

    boolean hasAstralSword();
    void setHasAstralSword(boolean has);

    // Blade aura: ticks remaining that the astral blades actively guard/attack
    int getBladeAuraTicks();
    void setBladeAuraTicks(int ticks);

    // Checkpoint for Time Loop Checkpoint / Rewind Self
    double getCheckpointX();
    double getCheckpointY();
    double getCheckpointZ();
    boolean hasCheckpoint();
    void setCheckpoint(double x, double y, double z);

    // Time Anchor
    boolean hasAnchor();
    void setAnchor(double x, double y, double z, int dimensionId);
    void clearAnchor();
    double getAnchorX();
    double getAnchorY();
    double getAnchorZ();
    int getAnchorDimension();

    // Second Chance (Green) armed shield flag
    boolean isSecondChanceArmed();
    void setSecondChanceArmed(boolean armed);

    // Countdown between "I AM THE GOD OF HYPERVERSE" and the actual revival completing
    int getAscensionTimer();
    void setAscensionTimer(int ticks);

    // World Rewind: last broken block
    void setLastBrokenBlock(BlockPos pos, net.minecraft.world.level.block.state.BlockState state);
    BlockPos getLastBrokenPos();
    net.minecraft.world.level.block.state.BlockState getLastBrokenState();

    void copyFrom(ITimeGodData other);

    net.minecraft.nbt.CompoundTag serializeNBT();
    void deserializeNBT(net.minecraft.nbt.CompoundTag tag);
}
