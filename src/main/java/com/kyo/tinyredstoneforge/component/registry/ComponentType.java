package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public class ComponentType<T extends PanelComponent> {
    private final String id;
    private final ComponentFactory<T> factory;

    public ComponentType(String id, ComponentFactory<T> factory) {
        this.id = id;
        this.factory = factory;
    }

    public String id() {
        return id;
    }

    public T create(int x, int y) {
        return factory.create(x, y);
    }
}