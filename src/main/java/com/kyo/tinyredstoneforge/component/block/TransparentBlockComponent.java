package com.kyo.tinyredstoneforge.component.block;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellVoxelShape;

public class TransparentBlockComponent extends PanelComponent {
    public TransparentBlockComponent(int x, int y) {
        super(x, y);
    }

    @Override
    public String getId() {
        return "transparent_block";
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public PanelCellVoxelShape getShape() {
        return PanelCellVoxelShape.FULLCELL;
    }
}
