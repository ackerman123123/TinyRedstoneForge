package com.kyo.tinyredstoneforge.placement;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;

public class PlacementManager {
    public PlacementResult place(PlacementContext context) {
        if (context.componentType() == null) {
            return PlacementResult.INVALID_COMPONENT;
        }

        PanelGrid grid = context.panel().engine().grid();

        if (!grid.isInside(context.x(), context.y())) {
            return PlacementResult.OUTSIDE_PANEL;
        }

        if (!grid.getCell(context.x(), context.y()).isEmpty()) {
            return PlacementResult.CELL_OCCUPIED;
        }

        PanelComponent component = context.componentType().create(context.x(), context.y());
        grid.getCell(context.x(), context.y()).setComponent(component);
        context.panel().setChanged();
        context.panel().engine().signals().markDirty();

        return PlacementResult.SUCCESS;
    }
}