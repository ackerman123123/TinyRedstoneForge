package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;

public final class ComponentTypes {
    public static final ComponentType<LeverComponent> LEVER =
            ComponentRegistry.register("lever", LeverComponent::new);

    public static final ComponentType<LampComponent> LAMP =
            ComponentRegistry.register("lamp", LampComponent::new);

    public static void init() {
        // Forces static initialization.
    }

    private ComponentTypes() {}
}