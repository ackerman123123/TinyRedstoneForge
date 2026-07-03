package com.kyo.tinyredstoneforge.renderer;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import java.util.ArrayList;
import java.util.List;

public class PanelRenderState {
    private final List<ComponentRenderData> components = new ArrayList<>();

    public static PanelRenderState fromGrid(PanelGrid grid) {
        PanelRenderState state = new PanelRenderState();

        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component == null) {
                    continue;
                }

                ComponentRenderer<PanelComponent> renderer =
                        ComponentRendererRegistry.get(component.getId());

                if (renderer != null) {
                    state.components.add(renderer.createRenderData(component));
                }
            }
        }

        return state;
    }

    public List<ComponentRenderData> components() {
        return components;
    }
}