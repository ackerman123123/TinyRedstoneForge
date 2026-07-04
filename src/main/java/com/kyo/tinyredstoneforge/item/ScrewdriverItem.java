package com.kyo.tinyredstoneforge.item;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;
import com.kyo.tinyredstoneforge.panel.PanelCellSegment;
import com.kyo.tinyredstoneforge.panel.PanelHitLocation;
import com.kyo.tinyredstoneforge.panel.PanelCell;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ScrewdriverItem extends Item {
    public ScrewdriverItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos();
        BlockEntity blockEntity = context.getLevel().getBlockEntity(pos);

        if (!(blockEntity instanceof PanelBlockEntity panel)) {
            return InteractionResult.PASS;
        }

        int x = PanelInteraction.cellX(context, panel);
        int y = PanelInteraction.cellY(context, panel);
        PanelCell cell = panel.engine().grid().getCell(x, y);

        if (cell.isEmpty()) {
            return InteractionResult.PASS;
        }

        PanelComponent component = cell.getComponent();

        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            cell.clear();
            PanelInteraction.sync(panel);
            context.getPlayer().sendSystemMessage(Component.literal("Removed component at " + x + ", " + y));
            return InteractionResult.SUCCESS;
        }

        if (context.getPlayer() != null) {
            PanelHitLocation hit = PanelHitLocation.fromHit(panel, new net.minecraft.world.phys.BlockHitResult(
                    context.getClickLocation(),
                    context.getClickedFace(),
                    context.getClickedPos(),
                    context.isInside()
            ));

            if (hit != null && component.hasActivation(context.getPlayer())) {
                if (component.onBlockActivated(hit.segment(), context.getPlayer())) {
                    PanelInteraction.sync(panel);
                    context.getPlayer().sendSystemMessage(Component.literal("Activated " + component.getId()));
                    return InteractionResult.SUCCESS;
                }
            }

            if (component instanceof WireComponent wire) {
                context.getPlayer().sendSystemMessage(Component.literal("Wire signal: " + wire.getSignalStrength()));
                return InteractionResult.SUCCESS;
            }

            if (component instanceof LampComponent lamp) {
                context.getPlayer().sendSystemMessage(Component.literal("Lamp " + (lamp.isLit() ? "lit" : "off") + " (" + lamp.getInputSignal() + ")"));
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
