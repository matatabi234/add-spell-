package com.matatabi.add_spell.client;

import com.matatabi.add_spell.network.SpellCastPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.CAST_SPELL_KEY != null &&
                KeyBindings.CAST_SPELL_KEY.isDown()) {

            // クライアント側ではサーバーに通知するだけ！
            SpellCastPacket.send();
        }
    }
}
