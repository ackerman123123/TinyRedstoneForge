package com.kyo.tinyredstoneforge.renderer;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public interface ComponentRenderer<T extends PanelComponent> {
    ComponentRenderData createRenderData(T component);
}