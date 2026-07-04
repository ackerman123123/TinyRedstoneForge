package com.kyo.tinyredstoneforge.component.input;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ButtonComponent extends PanelComponent {
    private final int pressDuration;
    private final String id;
    private int ticksRemaining;
    private Side baseSide = Side.BOTTOM;

    public ButtonComponent(int x, int y) {
        this(x, y, "button", 30);
    }

    protected ButtonComponent(int x, int y, String id, int pressDuration) {
        super(x, y);
        this.id = id;
        this.pressDuration = pressDuration;
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean isPowered() {
        return ticksRemaining > 0;
    }

    public int getSignalStrength() {
        return isPowered() ? 15 : 0;
    }

    @Override
    public boolean tick() {
        if (ticksRemaining <= 0) {
            return false;
        }

        ticksRemaining--;
        return ticksRemaining == 0;
    }

    @Override
    public boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        ticksRemaining = pressDuration;
        return true;
    }

    @Override
    public boolean hasActivation() {
        return true;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return outputDirection != baseSide.getOpposite() ? getSignalStrength() : 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return outputDirection == baseSide ? getSignalStrength() : 0;
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
    public PanelCellVoxelShape getShape() {
        return PanelCellVoxelShape.BUTTONSHAPE;
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putInt("ticksRemaining", ticksRemaining);
        tag.putString("baseSide", baseSide.name());
    }

    @Override
    public void load(CompoundTag tag) {
        ticksRemaining = tag.getInt("ticksRemaining").orElse(0);
        String baseSideName = tag.getString("baseSide").orElse("");
        baseSide = baseSideName.isEmpty() ? Side.BOTTOM : Side.valueOf(baseSideName);
    }
}
