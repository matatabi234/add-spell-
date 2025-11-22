package com.matatabi.add_spell.items.custom.slot;

import com.matatabi.add_spell.menu.ModMenus;
import com.matatabi.add_spell.spell.SpellNode;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class SpellBookContainer implements Container {

    private final ItemStack[] items;

    public SpellBookContainer(int size) {
        items = new ItemStack[size];
        for (int i = 0; i < size; i++) items[i] = ItemStack.EMPTY;
        nodes = new SpellNode[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = new SpellNode(SpellNode.NodeType.EMPTY);
        }
    }

    @Override
    public int getContainerSize() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return items[index];
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = items[index].split(count);
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = items[index];
        items[index] = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        items[index] = stack;
    }

    @Override
    public void setChanged() { }

    @Override
    public boolean stillValid(net.minecraft.world.entity.player.Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < items.length; i++) items[i] = ItemStack.EMPTY;
    }

    private final SpellNode[] nodes;

    public SpellNode getNode(int index) {
        return nodes[index];
    }

    public void setNode(int index, SpellNode node) {
        nodes[index] = node;
    }

    // 簡単な方向探索の例
    public SpellNode getNextNode(SpellNode node, String direction) {
        int index = Arrays.asList(nodes).indexOf(node);
        if (direction.equals("RIGHT")) {
            int nextIndex = index + 1;
            if (nextIndex < nodes.length) return nodes[nextIndex];
        }
        // 上下も追加可能
        return null;
    }
}