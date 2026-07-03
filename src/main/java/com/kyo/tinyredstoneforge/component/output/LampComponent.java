package com.kyo.tinyredstoneforge.component.output;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public class LampComponent extends PanelComponent {
    private int signalStrength;

    public LampComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "lamp";
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = Math.max(0, Math.min(15, signalStrength));
    }

    public boolean isLit() {
        return signalStrength > 0;
    }
}