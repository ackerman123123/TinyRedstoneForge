package com.kyo.tinyredstoneforge.blockentity;

import com.kyo.tinyredstoneforge.engine.PanelEngine;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import com.kyo.tinyredstoneforge.serialization.PanelSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.block.state.BlockState;

public class PanelBlockEntity extends BlockEntity {
    private final PanelEngine engine = new PanelEngine(8, 8);

    public PanelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PANEL.get(), pos, state);
    }

    public PanelEngine engine() {
        return engine;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("panel", CompoundTag.CODEC, PanelSerializer.save(engine.grid()));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read("panel", CompoundTag.CODEC).ifPresent(tag -> {
            PanelSerializer.load(engine.grid(), tag);
            engine.signals().markDirty();
        });
    }
}