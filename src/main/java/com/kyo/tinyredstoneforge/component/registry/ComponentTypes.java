package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.block.SolidBlockComponent;
import com.kyo.tinyredstoneforge.component.block.TransparentBlockComponent;
import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.input.ButtonComponent;
import com.kyo.tinyredstoneforge.component.input.StoneButtonComponent;
import com.kyo.tinyredstoneforge.component.logic.ComparatorComponent;
import com.kyo.tinyredstoneforge.component.logic.RepeaterComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.source.RedstoneBlockComponent;
import com.kyo.tinyredstoneforge.component.source.TorchComponent;
import com.kyo.tinyredstoneforge.component.wire.BridgeComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;

public final class ComponentTypes {
    public static final ComponentType<LeverComponent> LEVER =
            ComponentRegistry.register("lever", LeverComponent::new);

    public static final ComponentType<ButtonComponent> BUTTON =
            ComponentRegistry.register("button", ButtonComponent::new);

    public static final ComponentType<StoneButtonComponent> STONE_BUTTON =
            ComponentRegistry.register("stone_button", StoneButtonComponent::new);

    public static final ComponentType<RepeaterComponent> REPEATER =
            ComponentRegistry.register("repeater", RepeaterComponent::new);

    public static final ComponentType<ComparatorComponent> COMPARATOR =
            ComponentRegistry.register("comparator", ComparatorComponent::new);

    public static final ComponentType<LampComponent> LAMP =
            ComponentRegistry.register("lamp", LampComponent::new);

    public static final ComponentType<WireComponent> WIRE =
            ComponentRegistry.register("wire", WireComponent::new);

    public static final ComponentType<BridgeComponent> BRIDGE =
            ComponentRegistry.register("bridge", BridgeComponent::new);

    public static final ComponentType<RedstoneBlockComponent> REDSTONE_BLOCK =
            ComponentRegistry.register("redstone_block", RedstoneBlockComponent::new);

    public static final ComponentType<SolidBlockComponent> SOLID_BLOCK =
            ComponentRegistry.register("solid_block", SolidBlockComponent::new);

    public static final ComponentType<TransparentBlockComponent> TRANSPARENT_BLOCK =
            ComponentRegistry.register("transparent_block", TransparentBlockComponent::new);

    public static final ComponentType<TorchComponent> TORCH =
            ComponentRegistry.register("torch", TorchComponent::new);

    public static void init() {
        // Forces static initialization.
    }

    private ComponentTypes() {}
}
