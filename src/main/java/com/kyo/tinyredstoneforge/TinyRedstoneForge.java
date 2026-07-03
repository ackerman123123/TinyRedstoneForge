package com.kyo.tinyredstoneforge;

import com.kyo.tinyredstoneforge.component.registry.ComponentTypes;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import com.kyo.tinyredstoneforge.registry.ModBlocks;
import com.kyo.tinyredstoneforge.registry.ModItems;
import com.kyo.tinyredstoneforge.registry.ModTabs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TinyRedstoneForge.MODID)
public final class TinyRedstoneForge {
    public static final String MODID = "tinyredstoneforge";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TinyRedstoneForge(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        ModBlocks.BLOCKS.register(modBusGroup);
        ModItems.ITEMS.register(modBusGroup);
        ModTabs.TABS.register(modBusGroup);
        ModBlockEntities.BLOCK_ENTITIES.register(modBusGroup);

        ComponentTypes.init();

        LOGGER.info("Tiny Redstone Forge loaded");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Tiny Redstone Forge common setup");
    }
}