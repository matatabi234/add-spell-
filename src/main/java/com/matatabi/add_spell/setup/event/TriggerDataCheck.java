package com.matatabi.add_spell.setup.event;

import com.matatabi.add_spell.util.JsonLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class
TriggerDataCheck {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (JsonLoader.TRIGGER_DATA.isEmpty()) {
            player.sendSystemMessage(
                    net.minecraft.network.chat.Component.literal("TRIGGER_DATA は空です！")
            );
        } else {
            player.sendSystemMessage(
                    net.minecraft.network.chat.Component.literal("TRIGGER_DATA 読み込み成功！")
            );

            // 中身の確認も可能
            JsonLoader.TRIGGER_DATA.keySet().forEach(item ->
                    player.sendSystemMessage(
                            net.minecraft.network.chat.Component.literal("Loaded: " + item)
                    )
            );
        }
    }
}

