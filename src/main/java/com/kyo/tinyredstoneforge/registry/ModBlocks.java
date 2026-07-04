package com.kyo.tinyredstoneforge.registry;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import com.kyo.tinyredstoneforge.block.PanelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TinyRedstoneForge.MODID);

    public static final RegistryObject<Block> PANEL = BLOCKS.register("redstone_panel",
            () -> new PanelBlock(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("redstone_panel"))
                    .mapColor(MapColor.METAL)
                    .strength(1.5F, 6.0F)));

    private ModBlocks() {}
}
