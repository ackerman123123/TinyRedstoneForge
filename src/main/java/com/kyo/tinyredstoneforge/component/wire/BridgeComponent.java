package com.kyo.tinyredstoneforge.component.wire;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3d;

public class BridgeComponent extends PanelComponent {
    private int frontBackSignal;
    private int leftRightSignal;

    public BridgeComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "bridge";
    }

    public int getFrontBackSignal() {
        return frontBackSignal;
    }

    public int getLeftRightSignal() {
        return leftRightSignal;
    }

    public int getSignalStrength() {
        return Math.max(frontBackSignal, leftRightSignal);
    }

    public void clearSignals() {
        frontBackSignal = 0;
        leftRightSignal = 0;
    }

    public boolean setSignal(Side incomingSide, int signal) {
        int carriedSignal = Math.max(0, Math.min(15, signal - 1));

        if (incomingSide == Side.FRONT || incomingSide == Side.BACK) {
            if (carriedSignal <= frontBackSignal) {
                return false;
            }
            frontBackSignal = carriedSignal;
            return true;
        }

        if (incomingSide == Side.LEFT || incomingSide == Side.RIGHT) {
            if (carriedSignal <= leftRightSignal) {
                return false;
            }
            leftRightSignal = carriedSignal;
            return true;
        }

        return false;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        if (outputDirection == Side.FRONT || outputDirection == Side.BACK) {
            return frontBackSignal;
        }
        if (outputDirection == Side.LEFT || outputDirection == Side.RIGHT) {
            return leftRightSignal;
        }
        return 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return getWeakRsOutput(outputDirection);
    }

    @Override
    public boolean powerDrops() {
        return true;
    }

    @Override
    public boolean needsSolidBase() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return new PanelCellVoxelShape(new Vector3d(0.0D, 0.0D, 0.0D), new Vector3d(1.0D, 0.18D, 1.0D));
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("frontBackSignal", frontBackSignal);
        tag.putInt("leftRightSignal", leftRightSignal);
    }

    @Override
    public void load(CompoundTag tag) {
        frontBackSignal = tag.getInt("frontBackSignal").orElse(0);
        leftRightSignal = tag.getInt("leftRightSignal").orElse(0);
    }
}
