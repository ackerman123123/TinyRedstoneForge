package com.kyo.tinyredstoneforge.blockentity;

import com.kyo.tinyredstoneforge.engine.PanelEngine;
import com.kyo.tinyredstoneforge.engine.PanelContext;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import com.kyo.tinyredstoneforge.serialization.PanelSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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

    public void tick() {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (engine.signals().tick(new PanelContext(this))) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
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

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
