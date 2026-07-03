package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;

public final class ComponentTypes {
    public static final ComponentType<LeverComponent> LEVER =
            ComponentRegistry.register("lever", LeverComponent::new);

    public static final ComponentType<LampComponent> LAMP =
            ComponentRegistry.register("lamp", LampComponent::new);

    public static final ComponentType<WireComponent> WIRE =
            ComponentRegistry.register("wire", WireComponent::new);

    public static void init() {
        // Forces static initialization.
    }

    private ComponentTypes() {}
}