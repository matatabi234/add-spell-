package com.matatabi.add_spell.items.custom.slot;

import com.matatabi.add_spell.menu.SpellContainer;
import com.matatabi.add_spell.spell.SpellNode;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpellSlot extends SlotItemHandler {

    private final SpellNode node;
    private final List<Item> allowedItems;

    public SpellSlot(
            SpellContainer container,
            int index,
            int x,
            int y,
            SpellNode node,
            List<Item> allowedItems
    ) {
        super(container, index, x, y);
        this.node = node;
        this.allowedItems = allowedItems;
    }

    public SpellNode getNode() {
        return node;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return allowedItems != null && allowedItems.contains(stack.getItem());
    }

}