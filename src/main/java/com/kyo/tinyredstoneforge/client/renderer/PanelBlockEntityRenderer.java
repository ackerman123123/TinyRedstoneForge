package com.kyo.tinyredstoneforge.client.renderer;

import com.kyo.tinyredstoneforge.blockentity.PanelBlockEntity;
import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.block.SolidBlockComponent;
import com.kyo.tinyredstoneforge.component.block.TransparentBlockComponent;
import com.kyo.tinyredstoneforge.component.input.ButtonComponent;
import com.kyo.tinyredstoneforge.component.input.LeverComponent;
import com.kyo.tinyredstoneforge.component.input.StoneButtonComponent;
import com.kyo.tinyredstoneforge.component.logic.ComparatorComponent;
import com.kyo.tinyredstoneforge.component.logic.RepeaterComponent;
import com.kyo.tinyredstoneforge.component.output.LampComponent;
import com.kyo.tinyredstoneforge.component.source.RedstoneBlockComponent;
import com.kyo.tinyredstoneforge.component.source.TorchComponent;
import com.kyo.tinyredstoneforge.component.wire.BridgeComponent;
import com.kyo.tinyredstoneforge.component.wire.WireComponent;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class PanelBlockEntityRenderer implements BlockEntityRenderer<PanelBlockEntity, PanelRenderState> {
    private static final Identifier REDSTONE_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_redstone_segment.png");
    private static final Identifier LEVER_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/item/tiny_lever.png");
    private static final Identifier COBBLESTONE_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/redstone_panel.png");
    private static final Identifier LAMP_OFF_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/redstone_lamp.png");
    private static final Identifier LAMP_ON_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/redstone_lamp_on.png");
    private static final Identifier REDSTONE_BLOCK_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/redstone_block.png");
    private static final Identifier TORCH_ON_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/redstone_torch.png");
    private static final Identifier TORCH_OFF_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/redstone_torch_off.png");
    private static final Identifier REPEATER_ON_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_repeater_on.png");
    private static final Identifier REPEATER_OFF_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_repeater_off.png");
    private static final Identifier COMPARATOR_ON_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_comparator_on.png");
    private static final Identifier COMPARATOR_OFF_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_comparator_off.png");
    private static final Identifier COMPARATOR_SUBTRACT_ON_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_comparator_subtract_on.png");
    private static final Identifier COMPARATOR_SUBTRACT_OFF_TEXTURE =
            Identifier.fromNamespaceAndPath("tinyredstone", "textures/block/panel_comparator_subtract_off.png");
    private static final Identifier WOOD_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/oak_planks.png");
    private static final Identifier STONE_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/stone.png");
    private static final Identifier GLASS_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft", "textures/block/glass.png");
    private static final float PANEL_TOP = 0.126F;

    public PanelBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public PanelRenderState createRenderState() {
        return new PanelRenderState();
    }

    @Override
    public void extractRenderState(PanelBlockEntity panel, PanelRenderState state, float partialTick, Vec3 cameraPos,
                                   ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(panel, state, crumblingOverlay);

        PanelGrid grid = panel.engine().grid();
        List<PanelRenderState.CellVisual> cells = new ArrayList<>();

        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component instanceof LeverComponent lever) {
                    cells.add(visual(grid, x, y, "lever", lever.getSignalStrength(), lever.isPowered(), true, true, true, true));
                } else if (component instanceof WireComponent wire) {
                    cells.add(visual(grid, x, y, "wire", wire.getSignalStrength(), wire.isPowered(),
                            wire.isFrontEnabled(), wire.isRightEnabled(), wire.isBackEnabled(), wire.isLeftEnabled()));
                } else if (component instanceof LampComponent lamp) {
                    cells.add(visual(grid, x, y, "lamp", lamp.getInputSignal(), lamp.isLit(), true, true, true, true));
                } else if (component instanceof RedstoneBlockComponent) {
                    cells.add(visual(grid, x, y, "redstone_block", 15, true, true, true, true, true));
                } else if (component instanceof TorchComponent torch) {
                    cells.add(visual(grid, x, y, "torch", torch.isOutputting() ? 15 : 0, torch.isOutputting(), true, true, true, true));
                } else if (component instanceof RepeaterComponent repeater) {
                    cells.add(visual(grid, x, y, "repeater", repeater.getTicks(), repeater.isOutputting(), true, true, true, true));
                } else if (component instanceof ComparatorComponent comparator) {
                    cells.add(visual(grid, x, y, comparator.isSubtractMode() ? "comparator_subtract" : "comparator",
                            comparator.getOutputSignal(), comparator.getOutputSignal() > 0, true, true, true, true));
                } else if (component instanceof StoneButtonComponent button) {
                    cells.add(visual(grid, x, y, "stone_button", button.getSignalStrength(), button.isPowered(), true, true, true, true));
                } else if (component instanceof ButtonComponent button) {
                    cells.add(visual(grid, x, y, "button", button.getSignalStrength(), button.isPowered(), true, true, true, true));
                } else if (component instanceof BridgeComponent bridge) {
                    cells.add(visual(grid, x, y, "bridge", bridge.getSignalStrength(), bridge.getSignalStrength() > 0, true, true, true, true));
                } else if (component instanceof SolidBlockComponent solidBlock) {
                    cells.add(visual(grid, x, y, "solid_block", solidBlock.getSignalStrength(), solidBlock.getSignalStrength() > 0, true, true, true, true));
                } else if (component instanceof TransparentBlockComponent) {
                    cells.add(visual(grid, x, y, "transparent_block", 0, false, true, true, true, true));
                }
            }
        }

        state.cells = cells.toArray(PanelRenderState.CellVisual[]::new);
    }

    private static PanelRenderState.CellVisual visual(PanelGrid grid, int x, int y, String type, int signal, boolean active,
                                                      boolean frontEnabled, boolean rightEnabled,
                                                      boolean backEnabled, boolean leftEnabled) {
        return new PanelRenderState.CellVisual(
                x,
                y,
                type,
                signal,
                active,
                hasComponent(grid, x, y - 1),
                hasComponent(grid, x + 1, y),
                hasComponent(grid, x, y + 1),
                hasComponent(grid, x - 1, y),
                frontEnabled,
                rightEnabled,
                backEnabled,
                leftEnabled
        );
    }

    private static boolean hasComponent(PanelGrid grid, int x, int y) {
        return grid.isInside(x, y) && !grid.getCell(x, y).isEmpty();
    }

    @Override
    public void submit(PanelRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraRenderState) {
        for (PanelRenderState.CellVisual cell : state.cells) {
            submitCell(state, cell, poseStack, collector);
        }
    }

    private static void submitCell(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                   SubmitNodeCollector collector) {
        float minX = cell.x() / 8.0F + 0.015F;
        float minZ = cell.y() / 8.0F + 0.015F;
        float maxX = (cell.x() + 1) / 8.0F - 0.015F;
        float maxZ = (cell.y() + 1) / 8.0F - 0.015F;

        switch (cell.type()) {
            case "wire" -> submitWire(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "lever" -> submitLever(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "lamp" -> submitLamp(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "redstone_block" -> submitRedstoneBlock(state, poseStack, collector, minX, minZ, maxX, maxZ);
            case "torch" -> submitTorch(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "repeater" -> submitRepeater(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "comparator", "comparator_subtract" -> submitComparator(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "button", "stone_button" -> submitButton(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "bridge" -> submitBridge(state, cell, poseStack, collector, minX, minZ, maxX, maxZ);
            case "solid_block" -> submitSmallBlock(state, cell, poseStack, collector, minX, minZ, maxX, maxZ, STONE_TEXTURE, false);
            case "transparent_block" -> submitSmallBlock(state, cell, poseStack, collector, minX, minZ, maxX, maxZ, GLASS_TEXTURE, true);
            default -> {
            }
        }
    }

    private static void submitWire(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                   SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        float centerX = (minX + maxX) * 0.5F;
        float centerZ = (minZ + maxZ) * 0.5F;
        float half = 0.010F;
        float pad = 0.026F;
        int color = cell.signal() > 0 ? 0xFFFF3030 : 0xFF6A0808;
        RenderType renderType = RenderTypes.entityCutout(REDSTONE_TEXTURE);

        collector.submitCustomGeometry(poseStack, renderType, (pose, consumer) -> {
            quad(pose, consumer, centerX - pad, centerZ - pad, centerX + pad, centerZ + pad, PANEL_TOP + 0.004F, color, state.lightCoords);

            if (cell.frontEnabled() && (cell.north() || noWireConnections(cell))) {
                quad(pose, consumer, centerX - half, minZ, centerX + half, centerZ, PANEL_TOP + 0.005F, color, state.lightCoords);
            }
            if (cell.backEnabled() && (cell.south() || noWireConnections(cell))) {
                quad(pose, consumer, centerX - half, centerZ, centerX + half, maxZ, PANEL_TOP + 0.005F, color, state.lightCoords);
            }
            if (cell.leftEnabled() && (cell.west() || noWireConnections(cell))) {
                quad(pose, consumer, minX, centerZ - half, centerX, centerZ + half, PANEL_TOP + 0.006F, color, state.lightCoords);
            }
            if (cell.rightEnabled() && (cell.east() || noWireConnections(cell))) {
                quad(pose, consumer, centerX, centerZ - half, maxX, centerZ + half, PANEL_TOP + 0.006F, color, state.lightCoords);
            }
        });
    }

    private static boolean noWireConnections(PanelRenderState.CellVisual cell) {
        return !cell.north() && !cell.east() && !cell.south() && !cell.west();
    }

    private static void submitLever(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                    SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        float insetX = (maxX - minX) * 0.18F;
        float insetZ = (maxZ - minZ) * 0.20F;
        float baseMinX = minX + insetX;
        float baseMaxX = maxX - insetX;
        float baseMinZ = minZ + insetZ + 0.010F;
        float baseMaxZ = maxZ - insetZ + 0.010F;
        int handleColor = cell.active() ? 0xFFFFFFFF : 0xFFD8D8D8;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(COBBLESTONE_TEXTURE),
                (pose, consumer) -> box(pose, consumer, baseMinX, baseMinZ, baseMaxX, baseMaxZ, PANEL_TOP + 0.004F, PANEL_TOP + 0.026F, 0xFFE6E6E6, state.lightCoords));

        float stickMinX = (minX + maxX) * 0.5F - 0.006F;
        float stickMaxX = (minX + maxX) * 0.5F + 0.006F;
        float stickMinZ = cell.active() ? minZ + 0.018F : (minZ + maxZ) * 0.5F - 0.004F;
        float stickMaxZ = cell.active() ? (minZ + maxZ) * 0.5F + 0.020F : maxZ - 0.018F;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(LEVER_TEXTURE),
                (pose, consumer) -> box(pose, consumer, stickMinX, stickMinZ, stickMaxX, stickMaxZ, PANEL_TOP + 0.026F, PANEL_TOP + 0.090F, handleColor, state.lightCoords));
    }

    private static void submitLamp(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                   SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        Identifier texture = cell.active() ? LAMP_ON_TEXTURE : LAMP_OFF_TEXTURE;
        int color = cell.active() ? 0xFFFFFFFF : 0xFFE0E0E0;
        int light = cell.active() ? 0x00F000F0 : state.lightCoords;
        float inset = (maxX - minX) * 0.08F;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture),
                (pose, consumer) -> box(pose, consumer, minX + inset, minZ + inset, maxX - inset, maxZ - inset, PANEL_TOP + 0.004F, PANEL_TOP + 0.095F, color, light));
    }

    private static void submitRedstoneBlock(PanelRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                                            float minX, float minZ, float maxX, float maxZ) {
        float inset = (maxX - minX) * 0.05F;
        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(REDSTONE_BLOCK_TEXTURE),
                (pose, consumer) -> box(pose, consumer, minX + inset, minZ + inset, maxX - inset, maxZ - inset,
                        PANEL_TOP + 0.004F, PANEL_TOP + 0.120F, 0xFFFFFFFF, 0x00F000F0));
    }

    private static void submitTorch(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                    SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        Identifier texture = cell.active() ? TORCH_ON_TEXTURE : TORCH_OFF_TEXTURE;
        int light = cell.active() ? 0x00F000F0 : state.lightCoords;
        float centerX = (minX + maxX) * 0.5F;
        float centerZ = (minZ + maxZ) * 0.5F;
        float foot = (maxX - minX) * 0.12F;
        float head = (maxX - minX) * 0.18F;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture), (pose, consumer) -> {
            box(pose, consumer, centerX - foot, centerZ - foot, centerX + foot, centerZ + foot,
                    PANEL_TOP + 0.004F, PANEL_TOP + 0.095F, 0xFFFFFFFF, light);
            box(pose, consumer, centerX - head, centerZ - head, centerX + head, centerZ + head,
                    PANEL_TOP + 0.095F, PANEL_TOP + 0.128F, 0xFFFFFFFF, light);
        });
    }

    private static void submitRepeater(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                       SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        Identifier texture = cell.active() ? REPEATER_ON_TEXTURE : REPEATER_OFF_TEXTURE;
        int light = cell.active() ? 0x00F000F0 : state.lightCoords;
        float inset = (maxX - minX) * 0.03F;
        float centerX = (minX + maxX) * 0.5F;
        float post = (maxX - minX) * 0.08F;
        float firstPostZ = minZ + (maxZ - minZ) * 0.28F;
        float secondPostZ = minZ + (maxZ - minZ) * (0.42F + Math.min(cell.signal(), 8) * 0.045F);

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture), (pose, consumer) -> {
            box(pose, consumer, minX + inset, minZ + inset, maxX - inset, maxZ - inset,
                    PANEL_TOP + 0.003F, PANEL_TOP + 0.030F, 0xFFFFFFFF, light);
            box(pose, consumer, centerX - post, firstPostZ - post, centerX + post, firstPostZ + post,
                    PANEL_TOP + 0.030F, PANEL_TOP + 0.085F, 0xFFFFFFFF, light);
            box(pose, consumer, centerX - post, secondPostZ - post, centerX + post, secondPostZ + post,
                    PANEL_TOP + 0.030F, PANEL_TOP + 0.075F, 0xFFFFFFFF, light);
        });
    }

    private static void submitComparator(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                         SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        boolean subtract = "comparator_subtract".equals(cell.type());
        Identifier texture = subtract
                ? (cell.active() ? COMPARATOR_SUBTRACT_ON_TEXTURE : COMPARATOR_SUBTRACT_OFF_TEXTURE)
                : (cell.active() ? COMPARATOR_ON_TEXTURE : COMPARATOR_OFF_TEXTURE);
        int light = cell.active() ? 0x00F000F0 : state.lightCoords;
        float inset = (maxX - minX) * 0.03F;
        float centerX = (minX + maxX) * 0.5F;
        float post = (maxX - minX) * 0.065F;
        float frontZ = minZ + (maxZ - minZ) * 0.25F;
        float leftX = minX + (maxX - minX) * 0.32F;
        float rightX = minX + (maxX - minX) * 0.68F;
        float backZ = minZ + (maxZ - minZ) * 0.62F;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture), (pose, consumer) -> {
            box(pose, consumer, minX + inset, minZ + inset, maxX - inset, maxZ - inset,
                    PANEL_TOP + 0.003F, PANEL_TOP + 0.030F, 0xFFFFFFFF, light);
            box(pose, consumer, centerX - post, frontZ - post, centerX + post, frontZ + post,
                    PANEL_TOP + 0.030F, PANEL_TOP + 0.085F, 0xFFFFFFFF, light);
            box(pose, consumer, leftX - post, backZ - post, leftX + post, backZ + post,
                    PANEL_TOP + 0.030F, PANEL_TOP + 0.073F, 0xFFFFFFFF, light);
            box(pose, consumer, rightX - post, backZ - post, rightX + post, backZ + post,
                    PANEL_TOP + 0.030F, PANEL_TOP + 0.073F, 0xFFFFFFFF, light);
        });
    }

    private static void submitButton(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                     SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        Identifier texture = "stone_button".equals(cell.type()) ? STONE_TEXTURE : WOOD_TEXTURE;
        float insetX = (maxX - minX) * 0.22F;
        float insetZ = (maxZ - minZ) * 0.30F;
        float height = cell.active() ? 0.020F : 0.043F;
        int color = cell.active() ? 0xFFE6E6E6 : 0xFFFFFFFF;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture),
                (pose, consumer) -> box(pose, consumer, minX + insetX, minZ + insetZ, maxX - insetX, maxZ - insetZ,
                        PANEL_TOP + 0.004F, PANEL_TOP + height, color, state.lightCoords));
    }

    private static void submitBridge(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                     SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ) {
        float centerX = (minX + maxX) * 0.5F;
        float centerZ = (minZ + maxZ) * 0.5F;
        float rail = 0.012F;
        float pad = 0.025F;
        int color = cell.active() ? 0xFFFF3030 : 0xFF6A0808;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(REDSTONE_TEXTURE), (pose, consumer) -> {
            quad(pose, consumer, centerX - rail, minZ + pad, centerX + rail, maxZ - pad,
                    PANEL_TOP + 0.006F, color, state.lightCoords);
            quad(pose, consumer, minX + pad, centerZ - rail, maxX - pad, centerZ + rail,
                    PANEL_TOP + 0.026F, color, state.lightCoords);
            box(pose, consumer, centerX - 0.018F, centerZ - 0.018F, centerX + 0.018F, centerZ + 0.018F,
                    PANEL_TOP + 0.006F, PANEL_TOP + 0.030F, 0xFF3A1A1A, state.lightCoords);
        });
    }

    private static void submitSmallBlock(PanelRenderState state, PanelRenderState.CellVisual cell, PoseStack poseStack,
                                         SubmitNodeCollector collector, float minX, float minZ, float maxX, float maxZ,
                                         Identifier texture, boolean transparent) {
        float inset = (maxX - minX) * 0.05F;
        int color = transparent ? 0x99FFFFFF : (cell.active() ? 0xFFFFFFFF : 0xFFE5E5E5);
        int light = cell.active() ? 0x00F000F0 : state.lightCoords;

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutout(texture),
                (pose, consumer) -> box(pose, consumer, minX + inset, minZ + inset, maxX - inset, maxZ - inset,
                        PANEL_TOP + 0.004F, PANEL_TOP + 0.122F, color, light));
    }

    private static void box(PoseStack.Pose pose, VertexConsumer consumer, float minX, float minZ, float maxX, float maxZ,
                            float minY, float maxY, int color, int light) {
        quad(pose, consumer, minX, minZ, maxX, maxZ, maxY, color, light);
        side(pose, consumer, minX, minZ, maxX, minZ, minY, maxY, color, light, 0.0F, -1.0F);
        side(pose, consumer, maxX, minZ, maxX, maxZ, minY, maxY, color, light, 1.0F, 0.0F);
        side(pose, consumer, maxX, maxZ, minX, maxZ, minY, maxY, color, light, 0.0F, 1.0F);
        side(pose, consumer, minX, maxZ, minX, minZ, minY, maxY, color, light, -1.0F, 0.0F);
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer consumer, float minX, float minZ, float maxX, float maxZ,
                             float y, int color, int light) {
        vertex(pose, consumer, minX, y, minZ, color, 0.0F, 0.0F, light, 0.0F, 1.0F, 0.0F);
        vertex(pose, consumer, minX, y, maxZ, color, 0.0F, 1.0F, light, 0.0F, 1.0F, 0.0F);
        vertex(pose, consumer, maxX, y, maxZ, color, 1.0F, 1.0F, light, 0.0F, 1.0F, 0.0F);
        vertex(pose, consumer, maxX, y, minZ, color, 1.0F, 0.0F, light, 0.0F, 1.0F, 0.0F);
    }

    private static void side(PoseStack.Pose pose, VertexConsumer consumer, float x1, float z1, float x2, float z2,
                             float minY, float maxY, int color, int light, float nx, float nz) {
        vertex(pose, consumer, x1, minY, z1, color, 0.0F, 1.0F, light, nx, 0.0F, nz);
        vertex(pose, consumer, x2, minY, z2, color, 1.0F, 1.0F, light, nx, 0.0F, nz);
        vertex(pose, consumer, x2, maxY, z2, color, 1.0F, 0.0F, light, nx, 0.0F, nz);
        vertex(pose, consumer, x1, maxY, z1, color, 0.0F, 0.0F, light, nx, 0.0F, nz);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, int color,
                               float u, float v, int light, float nx, float ny, float nz) {
        consumer.addVertex(pose, x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, nx, ny, nz);
    }
}
