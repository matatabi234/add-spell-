package com.matatabi.add_spell.item.custom.slot;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class SpellBookContainer implements Container {

    private final ItemStack[] items;

    public SpellBookContainer(int size) {
        items = new ItemStack[size];
        for (int i = 0; i < size; i++) items[i] = ItemStack.EMPTY;
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
}