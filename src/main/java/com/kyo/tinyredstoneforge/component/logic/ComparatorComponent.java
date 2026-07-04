package com.kyo.tinyredstoneforge.component.logic;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3d;

public class ComparatorComponent extends PanelComponent {
    private boolean subtractMode;
    private int outputSignal;

    public ComparatorComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "comparator";
    }

    public boolean isSubtractMode() {
        return subtractMode;
    }

    public int getOutputSignal() {
        return outputSignal;
    }

    public boolean updateSignal(int backSignal, int sideSignal) {
        int nextSignal;
        if (subtractMode) {
            nextSignal = Math.max(0, backSignal - sideSignal);
        } else {
            nextSignal = backSignal >= sideSignal ? backSignal : 0;
        }

        if (outputSignal == nextSignal) {
            return false;
        }

        outputSignal = nextSignal;
        return true;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return outputDirection == Side.FRONT ? outputSignal : 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return getWeakRsOutput(outputDirection);
    }

    @Override
    public boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        subtractMode = !subtractMode;
        return true;
    }

    @Override
    public boolean hasActivation() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return new PanelCellVoxelShape(new Vector3d(0.0D, 0.0D, 0.0D), new Vector3d(1.0D, 0.35D, 1.0D));
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putBoolean("subtractMode", subtractMode);
        tag.putInt("outputSignal", outputSignal);
    }

    @Override
    public void load(CompoundTag tag) {
        subtractMode = tag.getBoolean("subtractMode").orElse(false);
        outputSignal = tag.getInt("outputSignal").orElse(0);
    }
}
