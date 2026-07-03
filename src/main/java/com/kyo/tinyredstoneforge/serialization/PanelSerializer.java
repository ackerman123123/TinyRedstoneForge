package com.kyo.tinyredstoneforge.serialization;

import com.kyo.tinyredstoneforge.component.PanelComponent;
import com.kyo.tinyredstoneforge.component.registry.ComponentRegistry;
import com.kyo.tinyredstoneforge.component.registry.ComponentType;
import com.kyo.tinyredstoneforge.panel.PanelGrid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public final class PanelSerializer {
    private PanelSerializer() {}

    public static CompoundTag save(PanelGrid grid) {
        CompoundTag root = new CompoundTag();
        ListTag components = new ListTag();

        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                PanelComponent component = grid.getCell(x, y).getComponent();

                if (component == null) {
                    continue;
                }

                CompoundTag tag = new CompoundTag();
                tag.putString("id", component.getId());
                tag.putInt("x", x);
                tag.putInt("y", y);
                component.save(tag);
                components.add(tag);
            }
        }

        root.put("components", components);
        return root;
    }

    public static void load(PanelGrid grid, CompoundTag root) {
        for (int y = 0; y < grid.height(); y++) {
            for (int x = 0; x < grid.width(); x++) {
                grid.getCell(x, y).clear();
            }
        }

        ListTag components = root.getList("components").orElse(new ListTag());

        for (int i = 0; i < components.size(); i++) {
            CompoundTag tag = components.getCompound(i).orElse(new CompoundTag());

            String id = tag.getString("id").orElse("");
            int x = tag.getInt("x").orElse(0);
            int y = tag.getInt("y").orElse(0);

            if (!grid.isInside(x, y)) {
                continue;
            }

            ComponentType<?> type = ComponentRegistry.get(id);

            if (type == null) {
                continue;
            }

            PanelComponent component = type.create(x, y);
            component.load(tag);
            grid.getCell(x, y).setComponent(component);
        }
    }
}