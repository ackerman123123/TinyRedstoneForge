package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PanelContext {
    private final PanelBlockEntity blockEntity;

    public PanelContext(PanelBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    public PanelBlockEntity blockEntity() {
        return blockEntity;
    }

    public Level level() {
        return blockEntity.getLevel();
    }

    public BlockPos pos() {
        return blockEntity.getBlockPos();
    }
}