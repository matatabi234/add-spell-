package com.matatabi.add_spell.network;

import com.matatabi.add_spell.spell.ServerSpellHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellCastPacket {

    public SpellCastPacket() {}

    public static void handle(SpellCastPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            // 🔥 ここで魔法発動のサーバー処理を呼ぶ
            ServerSpellHandler.castSpell(player);
        });
        ctx.get().setPacketHandled(true);
    }

    public static void send() {
        ModNetworking.INSTANCE.sendToServer(new SpellCastPacket());
    }
}
