package com.godoftime.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TimeGodProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<ITimeGodData> TIME_GOD_CAPABILITY =
            net.minecraftforge.common.capabilities.CapabilityManager.get(new net.minecraftforge.common.capabilities.CapabilityToken<>() {});

    private final ITimeGodData backend = new TimeGodData();
    private final LazyOptional<ITimeGodData> optional = LazyOptional.of(() -> backend);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TIME_GOD_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return backend.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.deserializeNBT(nbt);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
