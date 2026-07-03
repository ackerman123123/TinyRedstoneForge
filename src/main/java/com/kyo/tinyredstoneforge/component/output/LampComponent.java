package com.kyo.tinyredstoneforge.component.output;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public class LampComponent extends PanelComponent {
    private int inputSignal;

    public LampComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "lamp";
    }

    public int getInputSignal() {
        return inputSignal;
    }

    public void setInputSignal(int signal) {
        this.inputSignal = Math.max(0, Math.min(15, signal));
    }

    public boolean isLit() {
        return inputSignal > 0;
    }
}