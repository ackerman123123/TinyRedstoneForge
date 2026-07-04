package com.kyo.tinyredstoneforge.panel;

public enum Side {
    FRONT, RIGHT, BACK, LEFT, TOP, BOTTOM;

    public Side getOpposite() {
        return switch (this) {
            case FRONT -> BACK;
            case RIGHT -> LEFT;
            case BACK -> FRONT;
            case LEFT -> RIGHT;
            case TOP -> BOTTOM;
            case BOTTOM -> TOP;
        };
    }

    public Side rotateYCW() {
        return switch (this) {
            case FRONT -> RIGHT;
            case RIGHT -> BACK;
            case BACK -> LEFT;
            case LEFT -> FRONT;
            default -> this;
        };
    }

    public Side rotateYCCW() {
        return switch (this) {
            case FRONT -> LEFT;
            case RIGHT -> FRONT;
            case BACK -> RIGHT;
            case LEFT -> BACK;
            default -> this;
        };
    }

    public Side rotateBack() {
        return switch (this) {
            case FRONT -> TOP;
            case TOP -> BACK;
            case BACK -> BOTTOM;
            case BOTTOM -> FRONT;
            default -> this;
        };
    }

    public Side rotateForward() {
        return switch (this) {
            case FRONT -> BOTTOM;
            case BOTTOM -> BACK;
            case BACK -> TOP;
            case TOP -> FRONT;
            default -> this;
        };
    }
}
