package com.matatabi.add_spell.spell;

public class SpellGrid {

    private final int width;
    private final int height;
    private final SpellNode[][] grid;

    public SpellGrid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new SpellNode[width][height];

        // 初期化
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new SpellNode(SpellNode.NodeType.EMPTY);
            }
        }
    }

    public SpellNode getNode(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;
        return grid[x][y];
    }

    public void setNode(int x, int y, SpellNode node) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        grid[x][y] = node;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}