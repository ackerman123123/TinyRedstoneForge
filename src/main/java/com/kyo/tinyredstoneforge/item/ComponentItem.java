package com.kyo.tinyredstoneforge.item;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.registry.ComponentType;
import com.kyo.tinyredstoneforge.placement.PlacementContext;
import com.kyo.tinyredstoneforge.placement.PlacementManager;
import com.kyo.tinyredstoneforge.placement.PlacementResult;
import com.kyo.tinyredstoneforge.placement.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ComponentItem extends Item {
    private final ComponentType<?> componentType;
    private final PlacementManager placementManager = new PlacementManager();

    public ComponentItem(ComponentType<?> componentType, Properties properties) {
        super(properties);
        this.componentType = componentType;
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
        PlacementContext placementContext = new PlacementContext(panel, componentType, x, y, Rotation.NORTH);
        PlacementResult result = placementManager.place(placementContext);

        if (result != PlacementResult.SUCCESS) {
            return InteractionResult.FAIL;
        }

        PanelInteraction.sync(panel);
        if (context.getPlayer() != null) {
            context.getPlayer().sendSystemMessage(Component.literal("Placed " + componentType.id() + " at " + x + ", " + y));
        }
        return InteractionResult.SUCCESS;
    }
}
