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
                    .title(Component.translatable("itemGroup.tinyredstone"))
                    .icon(() -> ModItems.SCREWDRIVER.get().getDefaultInstance())
                    .displayItems((params, output) -> {
                        output.accept(ModItems.PANEL.get());
                        output.accept(ModItems.SCREWDRIVER.get());
                        output.accept(ModItems.TINY_LEVER.get());
                        output.accept(ModItems.TINY_WIRE.get());
                        output.accept(ModItems.TINY_LAMP.get());
                        output.accept(ModItems.TINY_REDSTONE_BLOCK.get());
                        output.accept(ModItems.TINY_REDSTONE_TORCH.get());
                        output.accept(ModItems.TINY_REPEATER.get());
                        output.accept(ModItems.TINY_BUTTON.get());
                        output.accept(ModItems.TINY_STONE_BUTTON.get());
                        output.accept(ModItems.TINY_COMPARATOR.get());
                        output.accept(ModItems.TINY_REDSTONE_BRIDGE.get());
                        output.accept(ModItems.TINY_SOLID_BLOCK.get());
                        output.accept(ModItems.TINY_TRANSPARENT_BLOCK.get());
                    })
                    .build());

    private ModTabs() {}
}
