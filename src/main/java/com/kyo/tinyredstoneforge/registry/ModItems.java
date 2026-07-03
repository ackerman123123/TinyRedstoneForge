package com.kyo.tinyredstoneforge.registry;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TinyRedstoneForge.MODID);

    public static final RegistryObject<Item> PANEL = ITEMS.register("panel",
            () -> new BlockItem(ModBlocks.PANEL.get(), new Item.Properties().setId(ITEMS.key("panel"))));

    public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver",
            () -> new Item(new Item.Properties().setId(ITEMS.key("screwdriver")).stacksTo(1)));

    private ModItems() {}
}