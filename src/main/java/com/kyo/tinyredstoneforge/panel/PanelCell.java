package com.kyo.tinyredstoneforge.panel;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public class PanelCell {
    private final int x;
    private final int y;
    private PanelComponent component;

    public PanelCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isEmpty() {
        return component == null;
    }

    public PanelComponent getComponent() {
        return component;
    }

    public void setComponent(PanelComponent component) {
        this.component = component;
        if (component != null) {
            component.setPosition(x, y);
        }
    }

    public void clear() {
        this.component = null;
    }
}
