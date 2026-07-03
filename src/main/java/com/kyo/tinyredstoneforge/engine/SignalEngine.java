package com.kyo.tinyredstoneforge.engine;

public class SignalEngine {
    private boolean dirty = true;

    public void markDirty() {
        dirty = true;
    }

    public void tick(PanelContext context) {
        if (!dirty) {
            return;
        }

        dirty = false;
    }
}