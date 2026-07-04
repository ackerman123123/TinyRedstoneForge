package com.kyo.tinyredstoneforge.client;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import com.kyo.tinyredstoneforge.client.renderer.PanelBlockEntityRenderer;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TinyRedstoneForge.MODID, value = Dist.CLIENT)
public final class ClientModEvents {
    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClientBootstrap.initialize();
        TinyRedstoneForge.LOGGER.info("Tiny Redstone Forge client initialized");
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PANEL.get(), PanelBlockEntityRenderer::new);
    }
}
