package com.matatabi.add_spell.command;

import com.matatabi.add_spell.json.ConnectableData;
import com.matatabi.add_spell.json.ItemData;
import com.matatabi.add_spell.json.SpellDataLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument; // 💡 NEW: ResourceLocationArgumentをインポート
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation; // 💡 NEW: ResourceLocationをインポート

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SpellDataCommand {

    private static final List<String> VALID_DIRECTIONS = Arrays.asList("UP", "DOWN", "LEFT", "RIGHT");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> spellDataCommand =
                Commands.literal("spell_data")
                        .requires(source -> source.hasPermission(2))

                        // ... (reload/statusサブコマンドは省略せず残す) ...
                        .then(Commands.literal("reload")
                                .executes(context -> reloadData(context.getSource()))
                        )
                        .then(Commands.literal("status")
                                .executes(context -> displayStatus(context.getSource()))
                        )

                        // 💡 修正箇所: /spell_data get <item_id> <direction>
                        .then(Commands.literal("get")
                                // ★★★ item_id の引数型を ResourceLocationArgument.id() に変更 ★★★
                                .then(RequiredArgumentBuilder.<CommandSourceStack, ResourceLocation>argument("item_id", ResourceLocationArgument.id())
                                        .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("direction", StringArgumentType.string())
                                                .suggests((context, builder) -> {
                                                    VALID_DIRECTIONS.forEach(builder::suggest);
                                                    return builder.buildFuture();
                                                })
                                                .executes(context -> getConnectableData(
                                                        context.getSource(),
                                                        ResourceLocationArgument.getId(context, "item_id"), // ★★★ 取得方法も変更 ★★★
                                                        StringArgumentType.getString(context, "direction")
                                                ))
                                        )
                                )
                        );

        dispatcher.register(spellDataCommand);
    }

    // --- getConnectableData メソッドのシグネチャとロジックを ResourceLocation に対応させる ---
    private static int getConnectableData(CommandSourceStack source, ResourceLocation itemIdRL, String direction) throws CommandSyntaxException {
        // ResourceLocationをStringに変換し、既存のデータマップのキーに合わせる
        String itemId = itemIdRL.toString();

        // 1. アイテムデータの存在確認
        ItemData data = SpellDataLoader.ALL_ITEM_DATA.get(itemId);
        if (data == null) {
            source.sendFailure(Component.literal("❌ エラー: アイテムID '" + itemId + "' はロードされていません。"));
            return 0;
        }

        // 2. 方向引数の検証
        String upperDirection = direction.toUpperCase(Locale.ROOT);
        if (!VALID_DIRECTIONS.contains(upperDirection)) {
            source.sendFailure(Component.literal("❌ エラー: 無効な方向 '" + direction + "'. 有効な方向は " + VALID_DIRECTIONS + " です。"));
            return 0;
        }

        // 3. 接続可能データの取得 (以下省略、前回と同じロジックでOK)
        ConnectableData connectable = data.getConnectable();
        List<String> connectionList = null;

        if (connectable != null) {
            switch (upperDirection) {
                case "UP":
                    connectionList = connectable.getUP();
                    break;
                case "DOWN":
                    connectionList = connectable.getDOWN();
                    break;
                case "LEFT":
                    connectionList = connectable.getLEFT();
                    break;
                case "RIGHT":
                    connectionList = connectable.getRIGHT();
                    break;
            }
        }

        // 4. 結果の表示 (以下省略、前回と同じロジックでOK)
        if (connectionList == null || connectionList.isEmpty()) {
            source.sendSuccess(() ->
                            Component.literal("ℹ️ " + itemId + " の " + upperDirection + " 接続先はありませんでした。")
                                    .withStyle(ChatFormatting.YELLOW)
                    , false);
            return 1;
        } else {
            MutableComponent result = Component.literal("✅ " + itemId + " [" + upperDirection + "] 接続先リスト: ")
                    .withStyle(ChatFormatting.AQUA)
                    .append(Component.literal(connectionList.toString()).withStyle(ChatFormatting.WHITE));

            source.sendSuccess(() -> result, false);
            return connectionList.size();
        }
    }

    // --- 既存の reloadData と displayStatus メソッドはそのまま残す ---
    private static int reloadData(CommandSourceStack source) {
        // ... (省略) ...
        SpellDataLoader.loadAllItemData();
        int count = SpellDataLoader.ALL_ITEM_DATA.size();
        source.sendSuccess(() ->
                        Component.literal("✅ [Add Spell Data] JSONデータが再ロードされました。")
                                .withStyle(ChatFormatting.GREEN)
                                .append(Component.literal(" (合計: " + count + "件)"))
                , true);
        return count;
    }

    private static int displayStatus(CommandSourceStack source) {
        // ... (省略) ...
        Map<String, ItemData> dataMap = SpellDataLoader.ALL_ITEM_DATA;
        int count = dataMap.size();

        source.sendSuccess(() -> Component.literal("--- [Add Spell Data Status] ---").withStyle(ChatFormatting.YELLOW), false);

        if (count == 0) {
            source.sendFailure(Component.literal("❌ アイテムデータは現在ロードされていません。"));
        } else {
            source.sendSuccess(() -> Component.literal("✅ ロード済みデータ数: " + count + "件").withStyle(ChatFormatting.AQUA), false);

            MutableComponent listComponent = Component.literal("ロード済みID: ").withStyle(ChatFormatting.GRAY);
            int max_display = 10;
            int i = 0;
            for (String id : dataMap.keySet()) {
                if (i < max_display) {
                    if (i > 0) {
                        listComponent.append(", ");
                    }
                    listComponent.append(Component.literal(id).withStyle(ChatFormatting.WHITE));
                    i++;
                } else {
                    listComponent.append("... (" + (count - max_display) + "件省略)");
                    break;
                }
            }
            source.sendSuccess(() -> listComponent, false);
        }
        return count;
    }
}