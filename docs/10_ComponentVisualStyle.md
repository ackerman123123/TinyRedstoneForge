# Component Visual Style

## Goal

Components on the panel should look close to Tiny Redstone style:
small, clean, flat micro-electronic parts placed on a panel grid.

## Panel

- 8x8 grid
- dark/metal base
- subtle cell borders
- components sit slightly above panel surface

## Wire

- thin red line
- powered state: bright red
- unpowered state: dark red
- supports connections: north, east, south, west

## Lever

- small square base
- tiny handle
- off state: handle down / dark
- on state: handle raised / highlighted

## Lamp

- small square or circular diode
- off state: dark red/gray
- on state: bright red/orange glow

## Renderer Plan

Each component renderer should produce:
- position on panel grid
- component type
- signal strength
- rotation
- connection data if needed

Rendering quality target:
Tiny Redstone-like micro components, but with clean Forge implementation.