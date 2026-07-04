package com.kyo.tinyredstoneforge.engine;

import com.kyo.tinyredstoneforge.component.block.SolidBlockComponent;
import com.kyo.tinyredstoneforge.component.logic.ComparatorComponent;
import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.logic.RepeaterComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.source.TorchComponent;
import com.kyo.tinyredstoneforge.component.wire.BridgeComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import com.kyo.tinyredstoneforge.panel.Side;
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

    public boolean tick(PanelContext context) {
        PanelGrid grid = context.blockEntity().engine().grid();

        if (tickComponents(grid)) {
            dirty = true;
        }

        if (!dirty) {
            return false;
        }

        clearSignals(grid);
        updateTorches(grid);
        updateRepeaters(grid);
        propagateSourceSignals(grid);
        updateComparators(grid);
        propagateSourceSignals(grid);
        updateLamps(grid);

        solver.solve(graph);
        dirty = false;
        return true;
    }

    private boolean tickComponents(PanelGrid grid) {
        boolean changed = false;

        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();
                if (component != null && component.tick()) {
                    changed = true;
                }
            }
        }

        return changed;
    }

    private void clearSignals(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component instanceof WireComponent wire) {
                    wire.setSignalStrength(0);
                }

                if (component instanceof BridgeComponent bridge) {
                    bridge.clearSignals();
                }

                if (component instanceof SolidBlockComponent solidBlock) {
                    solidBlock.clearSignal();
                }

                if (component instanceof LampComponent lamp) {
                    lamp.setInputSignal(0);
                }
            }
        }
    }

    private void propagateSourceSignals(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component == null) {
                    continue;
                }

                spreadFromSide(grid, x, y, Side.FRONT, component.getWeakRsOutput(Side.FRONT));
                spreadFromSide(grid, x, y, Side.RIGHT, component.getWeakRsOutput(Side.RIGHT));
                spreadFromSide(grid, x, y, Side.BACK, component.getWeakRsOutput(Side.BACK));
                spreadFromSide(grid, x, y, Side.LEFT, component.getWeakRsOutput(Side.LEFT));
                spreadFromSide(grid, x, y, Side.TOP, component.getWeakRsOutput(Side.TOP));
                spreadFromSide(grid, x, y, Side.BOTTOM, component.getWeakRsOutput(Side.BOTTOM));
            }
        }
    }

    private void updateTorches(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (!(component instanceof TorchComponent torch)) {
                    continue;
                }

                int inputX = x + offsetX(Side.BACK);
                int inputY = y + offsetY(Side.BACK);
                boolean powered = false;

                if (grid.isInside(inputX, inputY)) {
                    PanelComponent input = grid.getCell(inputX, inputY).getComponent();
                    if (input != null) {
                        powered = input.getWeakRsOutput(Side.FRONT) > 0 || input.getStrongRsOutput(Side.FRONT) > 0;
                        if (input instanceof WireComponent wire) {
                            powered = powered || wire.getSignalStrength() > 0;
                        }
                    }
                }

                torch.setInputPowered(powered);
            }
        }
    }

    private void updateRepeaters(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (!(component instanceof RepeaterComponent repeater)) {
                    continue;
                }

                boolean backPowered = hasSignalFrom(grid, x + offsetX(Side.BACK), y + offsetY(Side.BACK), Side.FRONT);
                boolean locked = hasSignalFrom(grid, x + offsetX(Side.LEFT), y + offsetY(Side.LEFT), Side.RIGHT)
                        || hasSignalFrom(grid, x + offsetX(Side.RIGHT), y + offsetY(Side.RIGHT), Side.LEFT);

                repeater.updateInput(backPowered, locked);
            }
        }
    }

    private void updateComparators(PanelGrid grid) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (!(component instanceof ComparatorComponent comparator)) {
                    continue;
                }

                int backSignal = signalFrom(grid, x + offsetX(Side.BACK), y + offsetY(Side.BACK), Side.FRONT);
                int sideSignal = Math.max(
                        signalFrom(grid, x + offsetX(Side.LEFT), y + offsetY(Side.LEFT), Side.RIGHT),
                        signalFrom(grid, x + offsetX(Side.RIGHT), y + offsetY(Side.RIGHT), Side.LEFT)
                );

                comparator.updateSignal(backSignal, sideSignal);
            }
        }
    }

    private boolean hasSignalFrom(PanelGrid grid, int x, int y, Side outputSide) {
        if (!grid.isInside(x, y)) {
            return false;
        }

        PanelComponent component = grid.getCell(x, y).getComponent();

        if (component == null) {
            return false;
        }

        return signalFrom(grid, x, y, outputSide) > 0;
    }

    private int signalFrom(PanelGrid grid, int x, int y, Side outputSide) {
        if (!grid.isInside(x, y)) {
            return 0;
        }

        PanelComponent component = grid.getCell(x, y).getComponent();

        if (component == null) {
            return 0;
        }

        int signal = Math.max(component.getWeakRsOutput(outputSide), component.getStrongRsOutput(outputSide));

        if (component instanceof WireComponent wire) {
            signal = Math.max(signal, wire.sideEnabled(outputSide) ? wire.getSignalStrength() : 0);
        }

        if (component instanceof BridgeComponent bridge) {
            signal = Math.max(signal, bridge.getWeakRsOutput(outputSide));
        }

        if (component instanceof SolidBlockComponent solidBlock) {
            signal = Math.max(signal, solidBlock.getSignalStrength());
        }

        return signal;
    }

    private void spreadFromSide(PanelGrid grid, int x, int y, Side side, int signal) {
        if (signal <= 0) {
            return;
        }

        int nx = x + offsetX(side);
        int ny = y + offsetY(side);

        if (!grid.isInside(nx, ny)) {
            return;
        }

        spreadSignal(grid, nx, ny, side.getOpposite(), signal);
    }

    private void spreadSignal(PanelGrid grid, int x, int y, Side incomingSide, int signal) {
        if (signal <= 0) {
            return;
        }

        PanelComponent component = grid.getCell(x, y).getComponent();

        if (component instanceof WireComponent wire) {
            if (!wire.sideEnabled(incomingSide)) {
                return;
            }

            int carriedSignal = component.powerDrops() ? signal - 1 : signal;

            if (carriedSignal > wire.getSignalStrength()) {
                wire.setSignalStrength(carriedSignal);

                for (Side side : new Side[] { Side.FRONT, Side.RIGHT, Side.BACK, Side.LEFT }) {
                    if (side != incomingSide && wire.sideEnabled(side)) {
                        spreadFromSide(grid, x, y, side, carriedSignal);
                    }
                }
            }

            return;
        }

        if (component instanceof BridgeComponent bridge) {
            if (bridge.setSignal(incomingSide, signal)) {
                spreadFromSide(grid, x, y, incomingSide.getOpposite(), signal - 1);
            }

            return;
        }

        if (component instanceof SolidBlockComponent solidBlock) {
            if (solidBlock.acceptSignal(signal)) {
                for (Side side : new Side[] { Side.FRONT, Side.RIGHT, Side.BACK, Side.LEFT }) {
                    if (side != incomingSide) {
                        spreadFromSide(grid, x, y, side, signal);
                    }
                }
            }

            return;
        }

        if (component instanceof LampComponent lamp) {
            lamp.setInputSignal(Math.max(lamp.getInputSignal(), signal));
            return;
        }
    }

    private int offsetX(Side side) {
        return switch (side) {
            case RIGHT -> 1;
            case LEFT -> -1;
            default -> 0;
        };
    }

    private int offsetY(Side side) {
        return switch (side) {
            case FRONT -> -1;
            case BACK -> 1;
            default -> 0;
        };
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
                }

                lamp.setInputSignal(strongestSignal);
                lamp.neighborChanged();
            }
        }
    }
}
