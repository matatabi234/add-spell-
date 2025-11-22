package com.matatabi.add_spell.menu;

import com.matatabi.add_spell.item.ModItems;
import com.matatabi.add_spell.item.custom.SpellBookManager;
import com.matatabi.add_spell.item.custom.slot.SpellBookContainer;
import com.matatabi.add_spell.item.custom.slot.SpellSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SpellBookMenu extends AbstractContainerMenu {

    private final ItemStack bookStack;
    private final SpellBookContainer spellContainer;

    public SpellBookMenu(int windowId, Inventory playerInventory, ItemStack bookStack) {
        super(ModMenus.SPELLBOOK_MENU.get(), windowId);
        this.bookStack = bookStack;

        this.spellContainer = new SpellBookContainer(15);

        loadFromItem(bookStack);

        List<Item> allowedItems = List.of(ModItems.TEST_ITEM.get());
        int cols = 5, rows = 2, slotSize = 18;
        int startX = 43, startY = 17;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int i = row * cols + col;
                addSlot(new SpellSlot(spellContainer, i,
                        startX + col * slotSize,
                        startY + row * slotSize,
                        allowedItems));
            }
        }

        layoutPlayerInventorySlots(playerInventory, 8, 84);
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

            // SpellSlot の場合は allowedItems をチェック
            if (slot instanceof SpellSlot spellSlot) {
                if (!spellSlot.mayPlace(stackInSlot)) {
                    return ItemStack.EMPTY; // 移動不可
                }
            }

            // プレイヤーインベントリ ↔ メニュー内スロットの移動
            if (index < spellContainer.getContainerSize()) { // Menu側からプレイヤーへ
                if (!moveItemStackTo(stackInSlot, spellContainer.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // プレイヤーから Menu側へ
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
    public boolean stillValid(Player player) { return true; }

    @Override
    public void removed(Player player) {
        super.removed(player);
        saveToItem(); // GUI を閉じたら NBT 保存
    }

        private void loadFromItem(ItemStack stack) {
            CompoundTag tag = stack.getOrCreateTag();
            ListTag items = tag.getList("SpellItems", Tag.TAG_COMPOUND);
            for (int i = 0; i < items.size() && i < spellContainer.getContainerSize(); i++) {
                spellContainer.setItem(i, ItemStack.of(items.getCompound(i)));
            }
        }


    private void saveToItem() {
        ListTag items = new ListTag();
        for (int i = 0; i < spellContainer.getContainerSize(); i++) {
            items.add(spellContainer.getItem(i).serializeNBT());
        }
        bookStack.getOrCreateTag().put("SpellItems", items);
    }
}
