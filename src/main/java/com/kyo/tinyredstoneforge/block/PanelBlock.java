package com.kyo.tinyredstoneforge.block;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.registry.ComponentType;
import com.kyo.tinyredstoneforge.component.registry.ComponentTypes;
import com.kyo.tinyredstoneforge.item.PanelInteraction;
import com.kyo.tinyredstoneforge.panel.PanelHitLocation;
import com.kyo.tinyredstoneforge.registry.ModBlockEntities;
import com.kyo.tinyredstoneforge.registry.ModItems;
import com.kyo.tinyredstoneforge.placement.PlacementContext;
import com.kyo.tinyredstoneforge.placement.PlacementManager;
import com.kyo.tinyredstoneforge.placement.PlacementResult;
import com.kyo.tinyredstoneforge.placement.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PanelBlock extends Block implements EntityBlock {
    private static final VoxelShape PANEL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    private final PlacementManager placementManager = new PlacementManager();

    public PanelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.PANEL.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.PANEL.get() ? (tickerLevel, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof PanelBlockEntity panel) {
                panel.tick();
            }
        } : null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PANEL_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PANEL_SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return PANEL_SHAPE;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof PanelBlockEntity panel)) {
            return InteractionResult.PASS;
        }

        ComponentType<?> componentType = componentTypeFor(stack.getItem());

        if (componentType == null) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        PanelHitLocation hitLocation = PanelHitLocation.fromHit(panel, hit);
        if (hitLocation == null) {
            return InteractionResult.FAIL;
        }

        int x = hitLocation.row();
        int y = hitLocation.column();
        PlacementResult result = placementManager.place(new PlacementContext(panel, componentType, x, y, Rotation.NORTH));

        if (result != PlacementResult.SUCCESS) {
            player.sendSystemMessage(Component.literal("Cell occupied at " + x + ", " + y));
            return InteractionResult.FAIL;
        }

        PanelInteraction.sync(panel);
        player.sendSystemMessage(Component.literal("Placed " + componentType.id() + " at " + x + ", " + y));

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    private static ComponentType<?> componentTypeFor(Item item) {
        if (item == ModItems.TINY_LEVER.get()) {
            return ComponentTypes.LEVER;
        }

        if (item == ModItems.TINY_WIRE.get()) {
            return ComponentTypes.WIRE;
        }

        if (item == ModItems.TINY_LAMP.get()) {
            return ComponentTypes.LAMP;
        }

        if (item == ModItems.TINY_REDSTONE_BLOCK.get()) {
            return ComponentTypes.REDSTONE_BLOCK;
        }

        if (item == ModItems.TINY_REDSTONE_TORCH.get()) {
            return ComponentTypes.TORCH;
        }

        if (item == ModItems.TINY_REPEATER.get()) {
            return ComponentTypes.REPEATER;
        }

        if (item == ModItems.TINY_BUTTON.get()) {
            return ComponentTypes.BUTTON;
        }

        if (item == ModItems.TINY_STONE_BUTTON.get()) {
            return ComponentTypes.STONE_BUTTON;
        }

        if (item == ModItems.TINY_COMPARATOR.get()) {
            return ComponentTypes.COMPARATOR;
        }

        if (item == ModItems.TINY_REDSTONE_BRIDGE.get()) {
            return ComponentTypes.BRIDGE;
        }

        if (item == ModItems.TINY_SOLID_BLOCK.get()) {
            return ComponentTypes.SOLID_BLOCK;
        }

        if (item == ModItems.TINY_TRANSPARENT_BLOCK.get()) {
            return ComponentTypes.TRANSPARENT_BLOCK;
        }

        return null;
    }
}
