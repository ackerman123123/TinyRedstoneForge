package com.kyo.tinyredstoneforge.component.input;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class LeverComponent extends PanelComponent {
    private boolean powered;
    private Side baseSide = Side.BOTTOM;

    public LeverComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "lever";
    }

    public boolean isPowered() {
        return powered;
    }

    public void toggle() {
        powered = !powered;
    }

    public int getSignalStrength() {
        return powered ? 15 : 0;
    }

    @Override
    public boolean neighborChanged() {
        return false;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return powered && outputDirection != baseSide.getOpposite() ? 15 : 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return powered && outputDirection == baseSide ? 15 : 0;
    }

    @Override
    public boolean isIndependentState() {
        return true;
    }

    @Override
    public boolean needsSolidBase() {
        return true;
    }

    @Override
    public Side getBaseSide() {
        return baseSide;
    }

    @Override
    public void setBaseSide(Side side) {
        baseSide = side;
    }

    @Override
    public boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        toggle();
        return true;
    }

    @Override
    public boolean hasActivation() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        if (baseSide == Side.TOP) {
            return PanelCellVoxelShape.BUTTONSHAPE_TOP;
        }
        if (baseSide == Side.FRONT) {
            return PanelCellVoxelShape.BUTTONSHAPE_FRONT;
        }
        return PanelCellVoxelShape.BUTTONSHAPE;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putBoolean("powered", powered);
        tag.putString("baseSide", baseSide.name());
    }

    @Override
    public void load(CompoundTag tag) {
        powered = tag.getBoolean("powered").orElse(false);
        String baseSideName = tag.getString("baseSide").orElse("");
        baseSide = baseSideName.isEmpty() ? Side.BOTTOM : Side.valueOf(baseSideName);
    }
}
