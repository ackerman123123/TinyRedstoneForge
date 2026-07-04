package com.kyo.tinyredstoneforge.panel;

import org.joml.Vector3d;

public class PanelCellVoxelShape {
    public static final PanelCellVoxelShape FULLCELL =
            new PanelCellVoxelShape(new Vector3d(0.0D, 0.0D, 0.0D), new Vector3d(1.0D, 1.0D, 1.0D));
    public static final PanelCellVoxelShape QUARTERCELLSLAB =
            new PanelCellVoxelShape(new Vector3d(0.0D, 0.0D, 0.0D), new Vector3d(1.0D, 0.25D, 1.0D));
    public static final PanelCellVoxelShape BUTTONSHAPE =
            new PanelCellVoxelShape(new Vector3d(0.25D, 0.0D, 0.25D), new Vector3d(0.75D, 0.25D, 0.75D));
    public static final PanelCellVoxelShape BUTTONSHAPE_TOP =
            new PanelCellVoxelShape(new Vector3d(0.25D, 0.75D, 0.25D), new Vector3d(0.75D, 1.0D, 0.75D));
    public static final PanelCellVoxelShape BUTTONSHAPE_FRONT =
            new PanelCellVoxelShape(new Vector3d(0.25D, 0.25D, 0.25D), new Vector3d(0.75D, 0.75D, 0.75D));

    private final Vector3d point1;
    private final Vector3d point2;

    public PanelCellVoxelShape(Vector3d point1, Vector3d point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Vector3d getPoint1() {
        return point1;
    }

    public Vector3d getPoint2() {
        return point2;
    }
}
