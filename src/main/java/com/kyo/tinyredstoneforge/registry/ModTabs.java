package com.kyo.tinyredstoneforge.registry;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TinyRedstoneForge.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tinyredstoneforge"))
                    .icon(() -> ModItems.SCREWDRIVER.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        output.accept(ModItems.PANEL.get());
                        output.accept(ModItems.SCREWDRIVER.get());
                    })
                    .build());

    private ModTabs() {}
}