package com.kyo.tinyredstoneforge.renderer;

public record ComponentRenderData(
        String componentId,
        int x,
        int y,
        int signalStrength
) {
}