package com.kyo.tinyredstoneforge.component.source;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.Side;

public class RedstoneBlockComponent extends PanelComponent {
    public RedstoneBlockComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "redstone_block";
    }

    @Override
    public boolean onPlace() {
        return true;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return getStrongRsOutput(outputDirection);
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return 15;
    }

    @Override
    public boolean isIndependentState() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }
}
