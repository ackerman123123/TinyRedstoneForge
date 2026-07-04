# Backlog

Gap analysis against the original Tiny Redstone mod (Fabric/NeoForge), based on a full source comparison. Grouped by priority for upcoming sprints.

## Missing Panel Components

- [ ] Piston
- [ ] Sticky Piston
- [ ] Observer
- [ ] Note Block (sound + GUI)
- [ ] Super Repeater
- [ ] Colorable / lossless wire variant (WireComponent currently has no color support)
- [ ] Colorable Solid Block (Tiny Color Selector integration)

## Missing Panel Covers

- [ ] IPanelCover API equivalent
- [ ] Dark Cover
- [ ] Light Cover
- [ ] Trim Cover

## Missing Items / Blocks

- [ ] Chopper Block + Chopper Block Entity (component crafting station)
- [ ] Chopper GUI / Menu / Screen
- [ ] Blueprint item (panel schematic copy/paste)
- [ ] Blueprint GUI
- [ ] Tiny Color Selector item
- [ ] Silicon / Silicon Compound crafting materials

## Crafting

- [x] Recipes for all currently implemented panel components (lever, wire, torch, redstone block, lamp, repeater, comparator, button, stone button, bridge, solid block, transparent block) + wrench
- [x] Silicon / Silicon Compound items registered + smelting/crafting recipes
- [ ] Recipes for Chopper, Blueprint (blocked on those items existing)
- [ ] Loot tables beyond redstone_panel

## Multiplayer / Networking

- [ ] Sync layer for panel cell state changes (equivalent of PanelCellSync)
- [ ] Repeater tick sync
- [ ] Rotation lock sync
- [ ] Tiny block color sync
- [ ] Clear panel sync
- [ ] Crash flag reset sync
- [ ] Blueprint sync
- [ ] Note block instrument sync
- [ ] Sound playback sync

## Reliability

- [ ] Panel overflow / crash protection (equivalent of PanelOverflowException)
- [ ] Panel crash GUI / recovery flow
- [ ] Rotation lock feature for components

## UX

- [ ] Toolbar overlay (component selection HUD)
- [ ] Clear panel confirmation GUI

## Compatibility

- [ ] Jade integration (block info overlay)
- [ ] TheOneProbe integration

## Tooling

- [ ] Datagen for blockstates/models/items (currently hand-written)

## Already Ported (for reference)

- [x] Redstone Panel block + block entity
- [x] Screwdriver / Wrench
- [x] Lever
- [x] Wire (basic, no color)
- [x] Redstone Torch
- [x] Redstone Block
- [x] Redstone Lamp
- [x] Repeater
- [x] Comparator
- [x] Button
- [x] Stone Button
- [x] Redstone Bridge
- [x] Solid Block
- [x] Transparent Block
- [x] Signal engine (graph/solver, custom architecture, not a 1:1 port)
- [x] Placement system
- [x] Panel renderer (in progress this sprint)
