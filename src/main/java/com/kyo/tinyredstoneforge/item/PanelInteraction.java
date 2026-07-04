package com.kyo.tinyredstoneforge.item;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.engine.PanelContext;
import com.kyo.tinyredstoneforge.panel.PanelHitLocation;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class PanelInteraction {
    private PanelInteraction() {
    }

    public static int cellX(UseOnContext context, PanelBlockEntity panel) {
        PanelHitLocation hit = PanelHitLocation.fromHit(panel, new net.minecraft.world.phys.BlockHitResult(
                context.getClickLocation(),
                context.getClickedFace(),
                context.getClickedPos(),
                context.isInside()
        ));
        return hit != null ? hit.row() : cellX(localHit(context), panel);
    }

    public static int cellX(Vec3 local, PanelBlockEntity panel) {
        PanelGrid grid = panel.engine().grid();
        return Mth.clamp((int) Math.floor(local.x() * grid.width()), 0, grid.width() - 1);
    }

    public static int cellY(UseOnContext context, PanelBlockEntity panel) {
        PanelHitLocation hit = PanelHitLocation.fromHit(panel, new net.minecraft.world.phys.BlockHitResult(
                context.getClickLocation(),
                context.getClickedFace(),
                context.getClickedPos(),
                context.isInside()
        ));
        return hit != null ? hit.column() : cellY(localHit(context), panel);
    }

    public static int cellY(Vec3 local, PanelBlockEntity panel) {
        PanelGrid grid = panel.engine().grid();
        return Mth.clamp((int) Math.floor(local.z() * grid.height()), 0, grid.height() - 1);
    }

    public static void sync(PanelBlockEntity panel) {
        panel.engine().signals().markDirty();
        panel.engine().tick(new PanelContext(panel));
        panel.setChanged();

        Level level = panel.getLevel();
        if (level != null) {
            level.sendBlockUpdated(panel.getBlockPos(), panel.getBlockState(), panel.getBlockState(), 3);
        }
    }

    public static Vec3 localHit(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Vec3 hit = context.getClickLocation();
        return hit.subtract(pos.getX(), pos.getY(), pos.getZ());
    }
}
