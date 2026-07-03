package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.panel.PanelGrid;

public class PanelEngine {
    private final PanelGrid grid;
    private final ComponentManager components;
    private final SignalEngine signals;

    public PanelEngine(int width, int height) {
        this.grid = new PanelGrid(width, height);
        this.components = new ComponentManager(grid);
        this.signals = new SignalEngine();
    }

    public PanelGrid grid() {
        return grid;
    }

    public ComponentManager components() {
        return components;
    }

    public SignalEngine signals() {
        return signals;
    }

    public void tick(PanelContext context) {
        signals.tick(context);
    }
}