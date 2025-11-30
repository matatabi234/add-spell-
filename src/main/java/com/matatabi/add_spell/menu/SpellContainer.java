package com.matatabi.add_spell.menu;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpellContainer implements IItemHandlerModifiable {

    private final List<ItemStack> items;
    private boolean valid;

    public SpellContainer(int size) {
        items = new ArrayList<>();
        for (int i = 0; i < size; i++) items.add(ItemStack.EMPTY);
        valid = false;
    }

    public int getContainerSize() { return items.size(); }
    public ItemStack getItem(int index) { return items.get(index); }
    public void setItem(int index, ItemStack stack) { items.set(index, stack); }
    public boolean isValid() { return valid; }
    public void setValid(boolean v) { valid = v; }

    // IItemHandlerModifiable の実装
    @Override
    public int getSlots() {
        return items.size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return items.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack existing = items.get(slot);
        if (!existing.isEmpty()) return stack; // 空きがなければそのまま返す
        if (!simulate) items.set(slot, stack.copy());
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack existing = items.get(slot);
        if (existing.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = existing.split(amount);
        if (!simulate) items.set(slot, existing.isEmpty() ? ItemStack.EMPTY : existing);
        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true; // ここは必要に応じて制限可能
    }
}
