package com.kyo.tinyredstoneforge.component.registry;

import com.kyo.tinyredstoneforge.component.PanelComponent;

public final class ComponentTypes {
    public static void init() {
        // Component types will be registered here.
    }

    private ComponentTypes() {}

    public static final class EmptyComponent extends PanelComponent {
        public EmptyComponent(int x, int y) {
            super(x, y);
        }

        @Override
        public String getId() {
            return "empty";
        }
    }
}