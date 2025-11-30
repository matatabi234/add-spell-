package com.matatabi.add_spell.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

// 必要なインポートに絞り込み・変更
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects; // NullPointerException 対策として追加

// SpellDataLoadList.* の static import は削除します

public class SpellDataLoader {

    // 💡 修正1: リソース内のベースパスを定義 (src/main/resources/ の下)
    // JSONファイルが data/add_spell/items/ にあるという前提に基づきます
    private static final String RESOURCE_BASE_PATH = "data/add_spell/items/";

    // 💡 修正1: ファイル名リストのJSONファイルのリソースパス
    private static final String FILE_LIST_RESOURCE_PATH = RESOURCE_BASE_PATH + "Spell_ID.json";

    public static final Map<String, ItemData> ALL_ITEM_DATA = new HashMap<>();

    // ==========================================================
    // 🛠️ 修正されたメソッド群
    // ==========================================================

    /**
     * JSONファイルから文字列のリスト（ファイル名リスト）を読み込みます。
     * 🚨 修正: FileReader の代わりに InputStreamReader を使用
     * @param resourcePath 読み込むリソースファイルの相対パス
     */
    public static List<String> loadFileNameList(String resourcePath) {
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        Gson gson = new Gson();

        // クラスローダーを使ってリソースから直接読み込む
        try (Reader reader = new InputStreamReader(
                // 💡 クラスローダーを使ってリソースを取得
                Objects.requireNonNull(SpellDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)),
                StandardCharsets.UTF_8 // 文字コード指定
        )) {
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.err.println("❌ リソースファイル名リストの読み込みに失敗しました: " + resourcePath + " -> " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 個別のJSONファイル（アイテム設定）を読み込み、ItemDataオブジェクトに変換します。
     * 🚨 修正: FileReader の代わりに InputStreamReader を使用
     * @param resourcePath 読み込むリソースファイルの相対パス
     */
    public static ItemData loadItemData(String resourcePath) {
        Gson gson = new Gson();

        try (Reader reader = new InputStreamReader(
                // 💡 クラスローダーを使ってリソースを取得
                Objects.requireNonNull(SpellDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)),
                StandardCharsets.UTF_8
        )) {
            return gson.fromJson(reader, ItemData.class);
        } catch (Exception e) {
            System.err.println("❌ 個別アイテムデータの読み込みに失敗しました: " + resourcePath + " -> " + e.getMessage());
            return null;
        }
    }

    /**
     * ファイル名リストを取得し、全ての個別ファイルを読み込むメイン処理
     */
    public static void loadAllItemData() {

        System.out.println("--- JSONロード開始 ---");
        System.out.println("リソースベースパス: " + RESOURCE_BASE_PATH);
        System.out.println("ファイル名リストパス: " + FILE_LIST_RESOURCE_PATH);

        // 1. ファイル名リストを取得 (Spell_ID.jsonを読み込みます)
        List<String> itemFileNames = loadFileNameList(FILE_LIST_RESOURCE_PATH);

        if (itemFileNames.isEmpty()) {
            System.err.println("アイテムファイル名リストが空のため、読み込みを中止します。");
            return;
        } else {
            System.out.println("アイテムファイル名リストを " + itemFileNames.size() + " 件読み込みました。");
        }


        ALL_ITEM_DATA.clear();

        // 2. ファイル名リストを一つずつ処理（ループ）
        for (String fileName : itemFileNames) {
            // ファイルパスを結合 (例: "data/add_spell/items/single_data.json")
            String fullResourcePath = RESOURCE_BASE_PATH + fileName;

            // 3. 個別のJSONファイルを ItemData オブジェクトとして読み込む
            ItemData data = loadItemData(fullResourcePath);

            if (data != null) {
                // 4. 読み込んだデータを Map に保存 (キーは ItemData の ID)
                ALL_ITEM_DATA.put(data.getItem_id(), data);
                System.out.println("✅ 成功: " + data.getItem_id() + " の設定をロードしました。");
            } else {
                System.err.println("❌ 失敗: " + fileName + " の読み込みに失敗しました。");
            }
        }

        System.out.println("--- 全アイテムデータのロード完了。計 " + ALL_ITEM_DATA.size() + " 件 ---");

        // ==========================================================
        // 💡 データ確認コード
        // ==========================================================
        String testId = "add_spell:single";

        if (ALL_ITEM_DATA.containsKey(testId)) {
            ItemData data = ALL_ITEM_DATA.get(testId);

            System.out.println("\n✅ テストデータ確認: " + testId);
            System.out.println("   - Radius (半径): " + data.getRadius()); // 期待値: 5

            ConnectableData connect = data.getConnectable();
            if (connect != null) {
                // 期待値: [add_spell:single, add_spell:test_item]
                System.out.println("   - DOWN接続先リスト: " + connect.getDOWN());
            }
        } else {
            System.err.println("❌ テストアイテム [" + testId + "] のデータがMapに見つかりません。");
        }
    }
}