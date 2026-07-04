package com.kyo.tinyredstoneforge.panel;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public record PanelHitLocation(
        int row,
        int column,
        int level,
        double cellX,
        double cellY,
        double cellZ,
        PanelCellSegment segment
) {
    public static PanelHitLocation fromHit(PanelBlockEntity panel, BlockHitResult hit) {
        BlockPos pos = panel.getBlockPos();
        Direction rayTraceDirection = hit.getDirection().getOpposite();
        Vec3 hitVec = hit.getLocation().add(
                rayTraceDirection.getStepX() * 0.001D,
                rayTraceDirection.getStepY() * 0.001D,
                rayTraceDirection.getStepZ() * 0.001D
        );

        double relX = hitVec.x - pos.getX();
        double relY = hitVec.y - pos.getY();
        double relZ = hitVec.z - pos.getZ();

        if (relX == 1.0D) {
            relX = 0.99D;
        }
        if (relY == 1.0D) {
            relY = 0.99D;
        }
        if (relZ == 1.0D) {
            relZ = 0.99D;
        }

        int row = Math.round((float) (relX * 8.0F) - 0.5F);
        int column = Math.round((float) (relZ * 8.0F) - 0.5F);
        int level = Math.round((float) (relY * 8.0F) - 0.5F) - 1;

        if (!panel.engine().grid().isInside(row, column)) {
            return null;
        }

        double cellX = (relX - (row / 8.0D)) * 8.0D;
        double cellY = ((relY - 1.0D / 8.0D) - (level / 8.0D)) * 8.0D;
        double cellZ = (relZ - (column / 8.0D)) * 8.0D;

        return new PanelHitLocation(row, column, level, cellX, cellY, cellZ, segment(cellX, cellZ));
    }

    private static PanelCellSegment segment(double x, double z) {
        int segmentRow = Math.round((float) (x * 3.0F) - 0.5F);
        int segmentColumn = Math.round((float) (z * 3.0F) - 0.5F);

        if (segmentRow == 0) {
            return switch (segmentColumn) {
                case 0 -> PanelCellSegment.FRONT_RIGHT;
                case 1 -> PanelCellSegment.FRONT;
                case 2 -> PanelCellSegment.FRONT_LEFT;
                default -> null;
            };
        }

        if (segmentRow == 1) {
            return switch (segmentColumn) {
                case 0 -> PanelCellSegment.RIGHT;
                case 1 -> PanelCellSegment.CENTER;
                case 2 -> PanelCellSegment.LEFT;
                default -> null;
            };
        }

        if (segmentRow == 2) {
            return switch (segmentColumn) {
                case 0 -> PanelCellSegment.BACK_RIGHT;
                case 1 -> PanelCellSegment.BACK;
                case 2 -> PanelCellSegment.BACK_LEFT;
                default -> null;
            };
        }

        return null;
    }
}
