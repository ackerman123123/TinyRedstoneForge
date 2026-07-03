package com.kyo.tinyredstoneforge.panel;

public class PanelGrid {
    private final int width;
    private final int height;
    private final PanelCell[] cells;

    public PanelGrid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("PanelGrid size must be positive");
        }

        this.width = width;
        this.height = height;
        this.cells = new PanelCell[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[index(x, y)] = new PanelCell(x, y);
            }
        }
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public PanelCell getCell(int x, int y) {
        if (!isInside(x, y)) {
            throw new IndexOutOfBoundsException("Cell outside panel grid: " + x + ", " + y);
        }

        return cells[index(x, y)];
    }

    private int index(int x, int y) {
        return y * width + x;
    }
}