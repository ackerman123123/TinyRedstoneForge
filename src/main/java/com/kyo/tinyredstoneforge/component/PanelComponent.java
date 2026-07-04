package com.kyo.tinyredstoneforge.component;

import net.minecraft.nbt.CompoundTag;

public abstract class PanelComponent implements PanelCellBehavior {
    private int x;
    private int y;

    protected PanelComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String getId();

    @Override
    public int getWeakRsOutput(com.kyo.tinyredstoneforge.panel.Side outputDirection) {
        return 0;
    }

    @Override
    public int getStrongRsOutput(com.kyo.tinyredstoneforge.panel.Side outputDirection) {
        return 0;
    }

    public void save(CompoundTag tag) {
    }

    public void load(CompoundTag tag) {
    }
}
