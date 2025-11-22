package com.matatabi.add_spell.menu;

import com.matatabi.add_spell.items.ModItems;
import com.matatabi.add_spell.items.custom.slot.SpellBookContainer;
import com.matatabi.add_spell.items.custom.slot.SpellSlot;
import com.matatabi.add_spell.spell.SpellGrid;
import com.matatabi.add_spell.spell.SpellNode;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.matatabi.add_spell.spell.SpellValidator;

import java.util.ArrayList;
import java.util.List;

public class SpellBookMenu extends AbstractContainerMenu {

    private final ItemStack bookStack;
    private final SpellBookContainer spellContainer;

    public SpellBookMenu(int windowId, Inventory playerInventory, ItemStack bookStack) {
        super(ModMenus.SPELLBOOK_MENU.get(), windowId);
        this.bookStack = bookStack;
        this.spellContainer = new SpellBookContainer(15);

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

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int index = row * cols + col;
                SpellNode node = grid.getNode(col, row);
                this.addSlot(new SpellSlot(spellContainer, index,
                        startX + col * slotSize,
                        startY + row * slotSize,
                        node, // SpellNode を渡す
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

            if (slot instanceof SpellSlot spellSlot) {
                if (!spellSlot.mayPlace(stackInSlot)) {
                    return ItemStack.EMPTY; // 許可されていないアイテム
                }

                // 🔹 JSON情報を SpellNode に反映
                SpellNode node = spellSlot.getNode(); // SpellSlot に SpellNode 参照を持たせておく
                node.setItem(stackInSlot); // setItem 内で JsonLoader が呼ばれ triggerData がセットされる
            }

            // プレイヤーインベントリ ↔ メニュー内スロットの移動
            if (index < spellContainer.getContainerSize()) {
                if (!moveItemStackTo(stackInSlot, spellContainer.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
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
        saveToItem(); // NBT 保存

        // 魔法完成チェック
        boolean magicReady = SpellValidator.validate(spellContainer);
        player.getPersistentData().putBoolean("MagicReady", magicReady);
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
            ItemStack stack = spellContainer.getItem(i);
            if (stack != null && !stack.isEmpty()) {
                items.add(stack.serializeNBT());
            }
        }
        bookStack.getOrCreateTag().put("SpellItems", items);
    }
}
