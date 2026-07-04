package com.kyo.tinyredstoneforge.component.source;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;
import com.kyo.tinyredstoneforge.panel.Side;
import net.minecraft.nbt.CompoundTag;
import org.joml.Vector3d;

public class TorchComponent extends PanelComponent {
    private boolean output = true;
    private boolean burnout = false;
    private Side baseSide = Side.BOTTOM;

    public TorchComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "torch";
    }

    public boolean isOutputting() {
        return !burnout && output;
    }

    public void setInputPowered(boolean powered) {
        output = !powered;
    }

    @Override
    public int getWeakRsOutput(Side outputDirection) {
        if (burnout || !output) {
            return 0;
        }

        return outputDirection != Side.BACK ? 15 : 0;
    }

    @Override
    public int getStrongRsOutput(Side outputDirection) {
        return outputDirection == Side.TOP && !burnout && output ? 15 : 0;
    }

    @Override
    public int lightOutput() {
        return isOutputting() ? 1 : 0;
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
        return new PanelCellVoxelShape(new Vector3d(0.25D, 0.0D, 0.25D), new Vector3d(0.75D, 1.0D, 0.75D));
    }

    @Override
    public void save(CompoundTag tag) {
        tag.putBoolean("output", output);
        tag.putBoolean("burnout", burnout);
        tag.putString("baseSide", baseSide.name());
    }

    @Override
    public void load(CompoundTag tag) {
        output = tag.getBoolean("output").orElse(true);
        burnout = tag.getBoolean("burnout").orElse(false);
        String baseSideName = tag.getString("baseSide").orElse("");
        baseSide = baseSideName.isEmpty() ? Side.BOTTOM : Side.valueOf(baseSideName);
    }
}
