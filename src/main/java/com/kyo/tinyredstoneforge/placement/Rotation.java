package com.kyo.tinyredstoneforge.placement;

public enum Rotation {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Rotation next() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
}