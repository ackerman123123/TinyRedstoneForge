package com.kyo.tinyredstoneforge.renderer;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import java.util.HashMap;
import java.util.Map;

public final class ComponentRendererRegistry {
    private static final Map<String, ComponentRenderer<?>> RENDERERS = new HashMap<>();

    public static <T extends PanelComponent> void register(String componentId, ComponentRenderer<T> renderer) {
        RENDERERS.put(componentId, renderer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends PanelComponent> ComponentRenderer<T> get(String componentId) {
        return (ComponentRenderer<T>) RENDERERS.get(componentId);
    }

    private ComponentRendererRegistry() {}
}