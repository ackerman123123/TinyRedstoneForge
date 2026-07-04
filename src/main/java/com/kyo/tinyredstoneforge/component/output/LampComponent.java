package com.kyo.tinyredstoneforge.component.output;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;

public class LampComponent extends PanelComponent {
    private int inputSignal;
    private boolean lit;

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
        this.lit = inputSignal > 0;
    }

    public boolean isLit() {
        return lit;
    }

    @Override
    public boolean neighborChanged() {
        boolean wasLit = lit;
        lit = inputSignal > 0;
        return wasLit != lit;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return 0;
    }

    @Override
    public int lightOutput() {
        return lit ? 1 : 0;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return PanelCellVoxelShape.FULLCELL;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("inputSignal", inputSignal);
        tag.putBoolean("lit", lit);
    }

    @Override
    public void load(CompoundTag tag) {
        inputSignal = tag.getInt("inputSignal").orElse(0);
        lit = tag.getBoolean("lit").orElse(inputSignal > 0);
    }
}
