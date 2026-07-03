package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ComponentRegistry {
    private static final Map<String, ComponentType<?>> TYPES = new LinkedHashMap<>();

    public static <T extends PanelComponent> ComponentType<T> register(String id, ComponentFactory<T> factory) {
        if (TYPES.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate component type: " + id);
        }

        ComponentType<T> type = new ComponentType<>(id, factory);
        TYPES.put(id, type);
        return type;
    }

    public static ComponentType<?> get(String id) {
        return TYPES.get(id);
    }

    public static Collection<ComponentType<?>> all() {
        return TYPES.values();
    }

    private ComponentRegistry() {}
}