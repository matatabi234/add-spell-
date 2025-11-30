package com.matatabi.add_spell.spell;

import com.matatabi.add_spell.items.custom.CommonSpellBookItem;
import com.matatabi.add_spell.items.custom.slot.SpellBookContainer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ServerSpellHandler {
    public static void castSpell(ServerPlayer player) {
        ItemStack book = player.getMainHandItem();
        CompoundTag tag = book.getOrCreateTag();

        if (!tag.getBoolean("SpellValid")) {
            player.sendSystemMessage(Component.literal("魔法構成が無効です！"));
            return;
        }

        // 魔法が有効な場合だけ発動
        player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1f, 1f);
        player.sendSystemMessage(Component.literal("魔法を発動しました！"));
    }

}