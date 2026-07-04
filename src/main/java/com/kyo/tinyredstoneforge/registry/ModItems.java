package com.kyo.tinyredstoneforge.registry;

import com.kyo.tinyredstoneforge.TinyRedstoneForge;
import com.kyo.tinyredstoneforge.component.registry.ComponentTypes;
import com.kyo.tinyredstoneforge.item.ComponentItem;
import com.kyo.tinyredstoneforge.item.ScrewdriverItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TinyRedstoneForge.MODID);

    public static final RegistryObject<Item> PANEL = ITEMS.register("redstone_panel",
            () -> new BlockItem(ModBlocks.PANEL.get(), new Item.Properties().setId(ITEMS.key("redstone_panel"))));

    public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("redstone_wrench",
            () -> new ScrewdriverItem(new Item.Properties().setId(ITEMS.key("redstone_wrench")).stacksTo(1)));

    public static final RegistryObject<Item> TINY_LEVER = ITEMS.register("tiny_lever",
            () -> new ComponentItem(ComponentTypes.LEVER, new Item.Properties().setId(ITEMS.key("tiny_lever"))));

    public static final RegistryObject<Item> TINY_WIRE = ITEMS.register("tiny_redstone",
            () -> new ComponentItem(ComponentTypes.WIRE, new Item.Properties().setId(ITEMS.key("tiny_redstone"))));

    public static final RegistryObject<Item> TINY_LAMP = ITEMS.register("tiny_redstone_lamp",
            () -> new ComponentItem(ComponentTypes.LAMP, new Item.Properties().setId(ITEMS.key("tiny_redstone_lamp"))));

    public static final RegistryObject<Item> TINY_REDSTONE_BLOCK = ITEMS.register("tiny_redstone_block",
            () -> new ComponentItem(ComponentTypes.REDSTONE_BLOCK, new Item.Properties().setId(ITEMS.key("tiny_redstone_block"))));

    public static final RegistryObject<Item> TINY_REDSTONE_TORCH = ITEMS.register("tiny_redstone_torch",
            () -> new ComponentItem(ComponentTypes.TORCH, new Item.Properties().setId(ITEMS.key("tiny_redstone_torch"))));

    public static final RegistryObject<Item> TINY_REPEATER = ITEMS.register("tiny_repeater",
            () -> new ComponentItem(ComponentTypes.REPEATER, new Item.Properties().setId(ITEMS.key("tiny_repeater"))));

    public static final RegistryObject<Item> TINY_BUTTON = ITEMS.register("tiny_button",
            () -> new ComponentItem(ComponentTypes.BUTTON, new Item.Properties().setId(ITEMS.key("tiny_button"))));

    public static final RegistryObject<Item> TINY_STONE_BUTTON = ITEMS.register("tiny_stone_button",
            () -> new ComponentItem(ComponentTypes.STONE_BUTTON, new Item.Properties().setId(ITEMS.key("tiny_stone_button"))));

    public static final RegistryObject<Item> TINY_COMPARATOR = ITEMS.register("tiny_comparator",
            () -> new ComponentItem(ComponentTypes.COMPARATOR, new Item.Properties().setId(ITEMS.key("tiny_comparator"))));

    public static final RegistryObject<Item> TINY_REDSTONE_BRIDGE = ITEMS.register("tiny_redstone_bridge",
            () -> new ComponentItem(ComponentTypes.BRIDGE, new Item.Properties().setId(ITEMS.key("tiny_redstone_bridge"))));

    public static final RegistryObject<Item> TINY_SOLID_BLOCK = ITEMS.register("tiny_solid_block",
            () -> new ComponentItem(ComponentTypes.SOLID_BLOCK, new Item.Properties().setId(ITEMS.key("tiny_solid_block"))));

    public static final RegistryObject<Item> TINY_TRANSPARENT_BLOCK = ITEMS.register("tiny_transparent_block",
            () -> new ComponentItem(ComponentTypes.TRANSPARENT_BLOCK, new Item.Properties().setId(ITEMS.key("tiny_transparent_block"))));

    private ModItems() {}
}
