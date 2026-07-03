package com.kyo.tinyredstoneforge.blockentity;

import com.kyo.tinyredstoneforge.engine.PanelEngine;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PanelBlockEntity extends BlockEntity {
    private final PanelEngine engine = new PanelEngine(8, 8);

    public PanelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PANEL.get(), pos, state);
    }

    public PanelEngine engine() {
        return engine;
    }
}