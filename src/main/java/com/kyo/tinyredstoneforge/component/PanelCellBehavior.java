package com.kyo.tinyredstoneforge.component;

import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.world.entity.player.Player;

public interface PanelCellBehavior {
    default boolean onPlace() {
        return neighborChanged();
    }

    default boolean neighborChanged() {
        return false;
    }

    int getWeakRsOutput(Side outputDirection);

    int getStrongRsOutput(Side outputDirection);

    default boolean powerDrops() {
        return false;
    }

    default boolean isIndependentState() {
        return false;
    }

    default boolean isPushable() {
        return false;
    }

    default boolean canPlaceVertical() {
        return false;
    }

    default boolean needsSolidBase() {
        return false;
    }

    default boolean canAttachToBaseOnSide(Side side) {
        return true;
    }

    default Side getBaseSide() {
        return null;
    }

    default void setBaseSide(Side side) {
    }

    default int lightOutput() {
        return 0;
    }

    default boolean tick() {
        return false;
    }

    default boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        return false;
    }

    default boolean hasActivation(Player player) {
        return hasActivation();
    }

    default boolean hasActivation() {
        return false;
    }

    default PanelCellVoxelShape getShape() {
        return PanelCellVoxelShape.FULLCELL;
    }

    default PanelCellVoxelShape[] getShapes() {
        return new PanelCellVoxelShape[] { getShape() };
    }

    default void onRemove() {
    }
}
