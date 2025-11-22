package com.matatabi.add_spell.items.custom;

import com.matatabi.add_spell.items.custom.slot.SpellBookContainer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpellBookManager {

    private static final Map<UUID, SpellBookContainer> BOOKS = new HashMap<>();

    // UUID から SpellBookContainer を取得。なければ新規作成
    public static SpellBookContainer getContainer(UUID uuid) {
        return BOOKS.computeIfAbsent(uuid, k -> new SpellBookContainer(15)); // 15スロット
    }

    // GUI を閉じたときに container を保存
    public static void saveContainer(UUID uuid, SpellBookContainer container) {
        BOOKS.put(uuid, container);
    }
}
