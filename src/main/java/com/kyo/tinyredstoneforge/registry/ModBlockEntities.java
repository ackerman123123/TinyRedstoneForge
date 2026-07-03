package com.kyo.tinyredstoneforge.registry;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import java.util.Set;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TinyRedstoneForge.MODID);

    public static final RegistryObject<BlockEntityType<PanelBlockEntity>> PANEL =
            BLOCK_ENTITIES.register("panel", () ->
                    new BlockEntityType<>(PanelBlockEntity::new, Set.of(ModBlocks.PANEL.get())));

    private ModBlockEntities() {}
}