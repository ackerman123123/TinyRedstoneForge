# Architecture

Minecraft

↓

PanelBlock

↓

PanelBlockEntity

↓

PanelEngine

├── PlacementManager

├── ComponentManager

├── SignalEngine

└── PanelGrid

↓

PanelCell

↓

PanelComponent

↓

Lever

Wire

Lamp

The architecture is intentionally modular so new components can be added without changing the engine.