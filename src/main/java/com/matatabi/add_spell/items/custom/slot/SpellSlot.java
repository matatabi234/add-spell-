package com.matatabi.add_spell.items.custom.slot;

import com.matatabi.add_spell.spell.SpellNode;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpellSlot extends Slot {
    private final SpellNode node;
    private final List<Item> allowedItems;

    public SpellSlot(SpellBookContainer container, int index, int x, int y, SpellNode node, List<Item> allowedItems) {
        super(container, index, x, y); // 必ず Container を渡す
        this.node = node;
        this.allowedItems = allowedItems;
    }

    public SpellNode getNode() { return node; }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return allowedItems.contains(stack.getItem());
    }
}