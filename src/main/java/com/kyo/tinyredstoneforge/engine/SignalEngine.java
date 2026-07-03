package com.kyo.tinyredstoneforge.engine;

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

        solver.solve(graph);
        dirty = false;
    }
}