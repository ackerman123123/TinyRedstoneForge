package com.kyo.tinyredstoneforge.component.logic;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3d;

public class RepeaterComponent extends PanelComponent {
    private boolean input;
    private boolean output;
    private boolean locked;
    private int ticks = 2;

    public RepeaterComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "repeater";
    }

    public boolean isOutputting() {
        return output;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getTicks() {
        return ticks;
    }

    public boolean updateInput(boolean powered, boolean sideLocked) {
        input = powered;
        locked = sideLocked;

        if (locked || output == input) {
            return false;
        }

        output = input;
        return true;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        return output && outputDirection == Side.FRONT ? 15 : 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return getWeakRsOutput(outputDirection);
    }

    @Override
    public boolean onBlockActivated(PanelCellSegment segmentClicked, Player player) {
        ticks += 2;
        if (ticks > 8) {
            ticks = 2;
        }
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
        tag.putBoolean("input", input);
        tag.putBoolean("output", output);
        tag.putBoolean("locked", locked);
        tag.putInt("ticks", ticks);
    }

    @Override
    public void load(CompoundTag tag) {
        input = tag.getBoolean("input").orElse(false);
        output = tag.getBoolean("output").orElse(false);
        locked = tag.getBoolean("locked").orElse(false);
        ticks = tag.getInt("ticks").orElse(2);
    }
}
