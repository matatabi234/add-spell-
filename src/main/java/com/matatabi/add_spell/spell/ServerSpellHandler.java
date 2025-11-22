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
import com.matatabi.add_spell.util.JsonLoader;


public class ServerSpellHandler {



    public static void castSpell(ServerPlayer player) {
        // 右手に持っているSpellBookを取得
        ItemStack book = player.getMainHandItem();
        if (!(book.getItem() instanceof CommonSpellBookItem)) return;

        // SpellBookContainerにNBTから読み込む
        SpellBookContainer container = new SpellBookContainer(15);
        CompoundTag tag = book.getOrCreateTag();
        ListTag items = tag.getList("SpellItems", Tag.TAG_COMPOUND);
        for (int i = 0; i < items.size() && i < container.getContainerSize(); i++) {
            container.setItem(i, ItemStack.of(items.getCompound(i)));
        }

        boolean triggered = false;

        // 各スロットをチェックして、発動条件アイテムを探す
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                SpellNode.TriggerData data = JsonLoader.getTriggerData().get(item);
                // JsonLoader で定義したトリガーデータを確認
                if (JsonLoader.TRIGGER_DATA.containsKey(item)) {
                    triggered = true;
                    break;
                }
            }
        }

        if (triggered) {
            // 発動成功！エフェクトを表示
            player.level().playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1f, 1f);
            player.level().addParticle(ParticleTypes.FLAME, player.getX(), player.getY() + 1, player.getZ(), 0, 0, 0);
            player.sendSystemMessage(Component.literal("魔法を発動しました！"));
        } else {
            player.sendSystemMessage(Component.literal("発動条件がありません。"));
        }
    }
}