package com.matatabi.add_spell.item.custom.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpellSlot extends Slot {

    private final List<Item> allowedItems;

    public SpellSlot(Container container, int index, int x, int y, List<Item> allowedItems) {
        super(container, index, x, y);
        this.allowedItems = allowedItems;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return allowedItems.contains(stack.getItem());
    }
}
