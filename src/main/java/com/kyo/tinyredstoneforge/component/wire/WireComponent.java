package com.kyo.tinyredstoneforge.component.wire;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3d;

public class WireComponent extends PanelComponent {
    private int signalStrength;
    private boolean frontEnabled = true;
    private boolean rightEnabled = false;
    private boolean backEnabled = true;
    private boolean leftEnabled = false;

    public WireComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "wire";
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = Math.max(0, Math.min(15, signalStrength));
    }

    public boolean isPowered() {
        return signalStrength > 0;
    }

    public boolean sideEnabled(Side side) {
        return (side == Side.FRONT && frontEnabled)
                || (side == Side.RIGHT && rightEnabled)
                || (side == Side.BACK && backEnabled)
                || (side == Side.LEFT && leftEnabled);
    }

    public boolean isFrontEnabled() {
        return frontEnabled;
    }

    public boolean isRightEnabled() {
        return rightEnabled;
    }

    public boolean isBackEnabled() {
        return backEnabled;
    }

    public boolean isLeftEnabled() {
        return leftEnabled;
    }

    public void toggleFront() {
        frontEnabled = !frontEnabled;
    }

    public void toggleRight() {
        rightEnabled = !rightEnabled;
    }

    public void toggleBack() {
        backEnabled = !backEnabled;
    }

    public void toggleLeft() {
        leftEnabled = !leftEnabled;
    }

    public void toggleAll() {
        if (!frontEnabled || !rightEnabled || !backEnabled || !leftEnabled) {
            frontEnabled = true;
            rightEnabled = true;
            backEnabled = true;
            leftEnabled = true;
        } else {
            frontEnabled = false;
            rightEnabled = false;
            backEnabled = false;
            leftEnabled = false;
        }
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return sideEnabled(outputDirection) || outputDirection == Side.BOTTOM ? Math.max(signalStrength, 0) : 0;
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
    public boolean canAttachToBaseOnSide(Side side) {
        return side == Side.BOTTOM;
    }

    @Override
    public Side getBaseSide() {
        return Side.BOTTOM;
    }

    @Override
    public boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        if (segmentClicked == null) {
            return false;
        }

        switch (segmentClicked) {
            case FRONT -> toggleFront();
            case RIGHT -> toggleRight();
            case LEFT -> toggleLeft();
            case BACK -> toggleBack();
            case CENTER -> toggleAll();
            default -> {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean hasActivation() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return new PanelCellVoxelShape(new Vector3d(0.0D, 0.0D, 0.0D), new Vector3d(1.0D, 0.05D, 1.0D));
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("signalStrength", signalStrength);
        tag.putBoolean("front", frontEnabled);
        tag.putBoolean("right", rightEnabled);
        tag.putBoolean("back", backEnabled);
        tag.putBoolean("left", leftEnabled);
    }

    @Override
    public void load(CompoundTag tag) {
        signalStrength = tag.getInt("signalStrength").orElse(0);
        frontEnabled = tag.getBoolean("front").orElse(true);
        rightEnabled = tag.getBoolean("right").orElse(false);
        backEnabled = tag.getBoolean("back").orElse(true);
        leftEnabled = tag.getBoolean("left").orElse(false);
    }
}
