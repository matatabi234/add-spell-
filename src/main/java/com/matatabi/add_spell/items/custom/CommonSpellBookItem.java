package com.matatabi.add_spell.items.custom;

import com.matatabi.add_spell.menu.SpellBookMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class CommonSpellBookItem extends Item {

    public CommonSpellBookItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer && player.isShiftKeyDown()) {

            // GUI を開く
            NetworkHooks.openScreen(
                    serverPlayer,
                    new SimpleMenuProvider(
                            (id, inv, p) -> new SpellBookMenu(id, inv, stack),
                            Component.literal("Common Spell Book")
                    ),
                    buf -> buf.writeItem(stack) // ← アイテムそのものを送る
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
