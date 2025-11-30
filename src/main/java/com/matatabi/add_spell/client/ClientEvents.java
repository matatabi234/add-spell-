package com.matatabi.add_spell.client;

import com.matatabi.add_spell.network.SpellCastPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    private static boolean wasPressed = false;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.CAST_SPELL_KEY == null) return;

        boolean isPressed = KeyBindings.CAST_SPELL_KEY.isDown();

        // 押した瞬間だけ発動
        if (isPressed && !wasPressed) {
            SpellCastPacket.send();
        }

        // 次のフレーム用に記録
        wasPressed = isPressed;
    }
}


