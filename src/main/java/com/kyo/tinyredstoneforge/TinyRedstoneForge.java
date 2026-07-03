package com.kyo.tinyredstoneforge;

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

        LOGGER.info("Tiny Redstone Forge loaded");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Tiny Redstone Forge common setup");
    }
}