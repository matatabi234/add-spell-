package com.matatabi.add_spell.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {

    public static KeyMapping CAST_SPELL_KEY;

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        CAST_SPELL_KEY = new KeyMapping(
                "key.add_spell.cast_spell",          // 翻訳キー
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_R,               // デフォルト：R キー
                "key.categories.add_spell"          // カテゴリ
        );

        event.register(CAST_SPELL_KEY);
    }
}
