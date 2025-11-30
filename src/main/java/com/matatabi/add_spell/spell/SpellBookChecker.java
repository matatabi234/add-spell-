package com.matatabi.add_spell.spell;

import com.matatabi.add_spell.items.custom.CommonSpellBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SpellBookChecker {

    /**
     * SpellBook に valid フラグを保存する
     */
    public static void saveValidFlag(ServerPlayer player, boolean isValid) {
        ItemStack book = player.getMainHandItem();
        if (!(book.getItem() instanceof CommonSpellBookItem)) return;

        CompoundTag tag = book.getOrCreateTag();
        tag.putBoolean("SpellValid", isValid);
        book.setTag(tag);
    }

    /**
     * SpellBook の valid フラグを読み込む（魔法発動時に使う）
     */
    public static boolean isSpellValid(ItemStack book) {
        if (!book.hasTag()) return false;
        return book.getTag().getBoolean("SpellValid");
    }
}

