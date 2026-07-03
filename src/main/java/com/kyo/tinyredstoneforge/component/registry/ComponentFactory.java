package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.PanelComponent;

@FunctionalInterface
public interface ComponentFactory<T extends PanelComponent> {
    T create(int x, int y);
}