package com.matatabi.add_spell.network;

import com.matatabi.add_spell.MainClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetworking {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            MainClass.makeId("main_channel"), // ← これでOK
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int nextId() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.registerMessage(
                nextId(),
                SpellCastPacket.class,
                (msg, buf) -> {},                // 書き込み（今回は空）
                buf -> new SpellCastPacket(),    // 読み込み（引数なし）
                SpellCastPacket::handle,         // ハンドラ
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
