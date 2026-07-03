package com.kyo.tinyredstoneforge.item;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.registry.ComponentTypes;
import com.kyo.tinyredstoneforge.panel.PanelCell;
import com.kyo.tinyredstoneforge.placement.PlacementContext;
import com.kyo.tinyredstoneforge.placement.PlacementManager;
import com.kyo.tinyredstoneforge.placement.PlacementResult;
import com.kyo.tinyredstoneforge.placement.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ScrewdriverItem extends Item {
    private final PlacementManager placementManager = new PlacementManager();

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

        PanelCell cell = panel.engine().grid().getCell(0, 0);

        if (!cell.isEmpty()) {
            PanelComponent component = cell.getComponent();

            if (component instanceof LeverComponent lever) {
                lever.toggle();
                panel.setChanged();
                panel.engine().signals().markDirty();
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }

        PlacementContext placementContext = new PlacementContext(
                panel,
                ComponentTypes.LEVER,
                0,
                0,
                Rotation.NORTH
        );

        PlacementResult result = placementManager.place(placementContext);

        return result == PlacementResult.SUCCESS
                ? InteractionResult.SUCCESS
                : InteractionResult.FAIL;
    }
}