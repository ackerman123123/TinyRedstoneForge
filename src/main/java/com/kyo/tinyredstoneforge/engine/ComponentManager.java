package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;

public class ComponentManager {
    private final PanelGrid grid;

    public ComponentManager(PanelGrid grid) {
        this.grid = grid;
    }

    public boolean placeComponent(PanelComponent component, int x, int y) {
        if (!grid.isInside(x, y) || !grid.getCell(x, y).isEmpty()) {
            return false;
        }

        grid.getCell(x, y).setComponent(component);
        return true;
    }

    public boolean removeComponent(int x, int y) {
        if (!grid.isInside(x, y) || grid.getCell(x, y).isEmpty()) {
            return false;
        }

        grid.getCell(x, y).clear();
        return true;
    }
}