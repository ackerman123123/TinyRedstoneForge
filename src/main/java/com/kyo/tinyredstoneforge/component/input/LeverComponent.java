package com.kyo.tinyredstoneforge.component.input;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public class LeverComponent extends PanelComponent {
    private boolean powered;

    public LeverComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "lever";
    }

    public boolean isPowered() {
        return powered;
    }

    public void toggle() {
        powered = !powered;
    }

    public int getSignalStrength() {
        return powered ? 15 : 0;
    }
}