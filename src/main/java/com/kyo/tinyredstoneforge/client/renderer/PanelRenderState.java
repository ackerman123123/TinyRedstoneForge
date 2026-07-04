package com.kyo.tinyredstoneforge.client.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class PanelRenderState extends BlockEntityRenderState {
    public CellVisual[] cells = new CellVisual[0];

    public record CellVisual(
            int x,
            int y,
            String type,
            int signal,
            boolean active,
            boolean north,
            boolean east,
            boolean south,
            boolean west,
            boolean frontEnabled,
            boolean rightEnabled,
            boolean backEnabled,
            boolean leftEnabled
    ) {
    }
}
