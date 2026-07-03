package com.kyo.tinyredstoneforge.component.wire;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import net.minecraft.nbt.CompoundTag;

public class WireComponent extends PanelComponent {
    private int signalStrength;

    public WireComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "wire";
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = Math.max(0, Math.min(15, signalStrength));
    }

    public boolean isPowered() {
        return signalStrength > 0;
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
