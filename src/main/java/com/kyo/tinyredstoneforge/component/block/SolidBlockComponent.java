package com.kyo.tinyredstoneforge.component.block;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;

public class SolidBlockComponent extends PanelComponent {
    private int signalStrength;

    public SolidBlockComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "solid_block";
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void clearSignal() {
        signalStrength = 0;
    }

    public boolean acceptSignal(int signal) {
        int nextSignal = Math.max(0, Math.min(15, signal));
        if (nextSignal <= signalStrength) {
            return false;
        }

        signalStrength = nextSignal;
        return true;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return signalStrength;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return signalStrength;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return PanelCellVoxelShape.FULLCELL;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("signalStrength", signalStrength);
    }

    @Override
    public void load(CompoundTag tag) {
        signalStrength = tag.getInt("signalStrength").orElse(0);
    }
}
