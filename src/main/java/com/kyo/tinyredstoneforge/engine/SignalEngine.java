package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;
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

        clearSignals(grid);
        propagateLeverSignals(grid);
        updateLamps(grid);

        solver.solve(graph);
        dirty = false;
    }

    private void clearSignals(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component instanceof WireComponent wire) {
                    wire.setSignalStrength(0);
                }

                if (component instanceof LampComponent lamp) {
                    lamp.setInputSignal(0);
                }
            }
        }
    }

    private void propagateLeverSignals(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component instanceof LeverComponent lever && lever.isPowered()) {
                    spreadSignal(grid, x, y, 15);
                }
            }
        }
    }

    private void spreadSignal(PanelGrid grid, int x, int y, int signal) {
        if (signal <= 0) {
            return;
        }

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

            if (neighbor instanceof WireComponent wire) {
                if (signal - 1 > wire.getSignalStrength()) {
                    wire.setSignalStrength(signal - 1);
                    spreadSignal(grid, nx, ny, signal - 1);
                }
            }

            if (neighbor instanceof LampComponent lamp) {
                lamp.setInputSignal(Math.max(lamp.getInputSignal(), signal - 1));
            }
        }
    }

    private void updateLamps(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (!(component instanceof LampComponent lamp)) {
                    continue;
                }

                int strongestSignal = lamp.getInputSignal();

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

                    if (neighbor instanceof WireComponent wire) {
                        strongestSignal = Math.max(strongestSignal, wire.getSignalStrength());
                    }

                    if (neighbor instanceof LeverComponent lever) {
                        strongestSignal = Math.max(strongestSignal, lever.getSignalStrength());
                    }
                }

                lamp.setInputSignal(strongestSignal);
            }
        }
    }
}