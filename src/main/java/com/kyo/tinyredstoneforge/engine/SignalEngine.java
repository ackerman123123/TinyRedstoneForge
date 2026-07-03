package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import com.kyo.tinyredstoneforge.signal.SignalGraph;
import com.kyo.tinyredstoneforge.signal.SignalSolver;

public class SignalEngine {
    private final SignalGraph graph = new SignalGraph();
    private final SignalSolver solver = new SignalSolver();
    private boolean dirty = true;

    public SignalGraph graph() {
        return graph;
    }

    public void markDirty() {
        dirty = true;
    }

    public void tick(PanelContext context) {
        if (!dirty) {
            return;
        }

        PanelGrid grid = context.blockEntity().engine().grid();

        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component instanceof LampComponent lamp) {
                    lamp.setInputSignal(0);
                    applyNearbyLeverSignal(grid, x, y, lamp);
                }
            }
        }

        solver.solve(graph);
        dirty = false;
    }

    private void applyNearbyLeverSignal(PanelGrid grid, int x, int y, LampComponent lamp) {
        int[][] offsets = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        for (int[] offset : offsets) {
            int nx = x + offset[0];
            int ny = y + offset[1];

            if (!grid.isInside(nx, ny)) {
                continue;
            }

            PanelComponent neighbor = grid.getCell(nx, ny).getComponent();

            if (neighbor instanceof LeverComponent lever) {
                lamp.setInputSignal(Math.max(lamp.getInputSignal(), lever.getSignalStrength()));
            }
        }
    }
}