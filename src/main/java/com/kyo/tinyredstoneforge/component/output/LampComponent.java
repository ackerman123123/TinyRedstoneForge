package com.kyo.tinyredstoneforge.component.output;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import net.minecraft.nbt.CompoundTag;

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

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("inputSignal", inputSignal);
    }

    @Override
    public void load(CompoundTag tag) {
        inputSignal = tag.getInt("inputSignal").orElse(0);
    }
}
