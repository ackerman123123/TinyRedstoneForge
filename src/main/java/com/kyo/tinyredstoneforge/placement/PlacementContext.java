package com.kyo.tinyredstoneforge.placement;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.registry.ComponentType;

public record PlacementContext(
        PanelBlockEntity panel,
        ComponentType<?> componentType,
        int x,
        int y,
        Rotation rotation
) {
}