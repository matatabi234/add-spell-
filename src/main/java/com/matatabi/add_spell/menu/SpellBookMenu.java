package com.matatabi.add_spell.menu;

import com.matatabi.add_spell.items.ModItems;
import com.matatabi.add_spell.items.custom.CommonSpellBookItem;
import com.matatabi.add_spell.items.custom.slot.SpellBookContainer;
import com.matatabi.add_spell.items.custom.slot.SpellSlot;
import com.matatabi.add_spell.spell.SpellBookChecker;
//import com.matatabi.add_spell.spell.SpellGrid;
import com.matatabi.add_spell.spell.SpellGrid;
import com.matatabi.add_spell.spell.SpellNode;
import com.matatabi.add_spell.util.JsonLoader;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//import com.matatabi.add_spell.spell.SpellValidator;
//import com.matatabi.add_spell.spell.SpellLoader;

import java.util.ArrayList;
import java.util.List;

public class SpellBookMenu extends AbstractContainerMenu {

    private final ItemStack bookStack;
    private final SpellContainer spellContainer;
    private boolean spellValid;

    public SpellBookMenu(int windowId, Inventory playerInventory, ItemStack bookStack) {
        super(ModMenus.SPELLBOOK_MENU.get(), windowId);
        this.bookStack = bookStack;
        this.spellContainer = new SpellContainer(15); // ← SpellContainer に変更

        loadFromItem(bookStack);

        List<Item> allowedItems = new ArrayList<>();
        allowedItems.add(ModItems.TEST_ITEM.get());
        allowedItems.add(ModItems.AREA.get());
        allowedItems.add(ModItems.SINGLE.get());

        int cols = 5, rows = 2, slotSize = 18;
        int startX = 43, startY = 17;

        SpellGrid grid = new SpellGrid(cols, rows);
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid.setNode(x, y, new SpellNode(SpellNode.NodeType.EMPTY));
            }
        }

        // --- SpellContainer スロット ---
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                SpellNode node = grid.getNode(col, row);
                this.addSlot(new SpellSlot(
                        spellContainer, index,
                        startX + col * slotSize,
                        startY + row * slotSize,
                        node,
                        allowedItems
                ));

            }
        }

        layoutPlayerInventorySlots(playerInventory, 8, 84);

        spellValid = checkSpellGraph();
    }

    private void layoutPlayerInventorySlots(Inventory playerInventory, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, x + col * 18, y + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            // SpellSlot なら SpellNode に反映
            if (slot instanceof SpellSlot spellSlot) {
                if (!spellSlot.mayPlace(stackInSlot)) {
                    return ItemStack.EMPTY;
                }

                SpellNode node = spellSlot.getNode();
                node.setItem(stackInSlot);
            }

            if (index < spellContainer.getContainerSize()) {
                // Container → Player
                if (!moveItemStackTo(stackInSlot, spellContainer.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Player → Container
                if (!moveItemStackTo(stackInSlot, 0, spellContainer.getContainerSize(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return spellContainer.isValid();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide() && player instanceof ServerPlayer sp) {
            ItemStack spellBook = player.getMainHandItem();
            saveToItem(spellBook, spellContainer);
        }
    }

    /** Spell の接続判定（簡易版） */
    private boolean checkSpellGraph() {

        SpellContainer container = this.spellContainer;
        int width = 5;

        for (int i = 0; i < container.getContainerSize(); i++) {

            ItemStack center = container.getItem(i);
            if (center.isEmpty()) continue;

            int up = i - width;
            if (up >= 0 && !container.getItem(up).isEmpty()) return true;

            int down = i + width;
            if (down < container.getContainerSize() && !container.getItem(down).isEmpty()) return true;

            if (i % width != 0) {
                int left = i - 1;
                if (!container.getItem(left).isEmpty()) return true;
            }

            if (i % width != width - 1) {
                int right = i + 1;
                if (right < container.getContainerSize() && !container.getItem(right).isEmpty()) return true;
            }
        }

        return false;
    }

    /** ItemStack → SpellContainer */
    public void loadFromItem(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag items = tag.getList("SpellItems", Tag.TAG_COMPOUND);
        for (int i = 0; i < items.size() && i < spellContainer.getContainerSize(); i++) {
            spellContainer.setItem(i, ItemStack.of(items.getCompound(i)));
        }
        this.spellValid = tag.getBoolean("SpellValid");
        spellContainer.setValid(this.spellValid);
    }

    /** SpellContainer → ItemStack */
    public static void saveToItem(ItemStack stack, SpellContainer container) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag items = new ListTag();
        for (int i = 0; i < container.getContainerSize(); i++) {
            items.add(container.getItem(i).save(new CompoundTag()));
        }
        tag.put("SpellItems", items);
        tag.putBoolean("SpellValid", container.isValid());
    }
}
