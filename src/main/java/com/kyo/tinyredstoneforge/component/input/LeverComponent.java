package com.kyo.tinyredstoneforge.component.input;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import net.minecraft.nbt.CompoundTag;

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

    @Override
    public void save(CompoundTag tag) {
        tag.putBoolean("powered", powered);
    }

    @Override
    public void load(CompoundTag tag) {
        powered = tag.getBoolean("powered").orElse(false);
    }
}
