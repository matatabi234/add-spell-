package com.matatabi.add_spell.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.matatabi.add_spell.spell.SpellNode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonLoader {


    public static final Map<Item, SpellNode.TriggerData> TRIGGER_DATA = new HashMap<>();

    public static Map<Item, SpellNode.TriggerData> getTriggerData() {
        return TRIGGER_DATA;
    }

    public static void loadAll() {
        loadTriggerJson(new ResourceLocation("add_spell", "trigger_data"));
    }

    private static void loadTriggerJson(ResourceLocation loc) {
        try (InputStreamReader reader = new InputStreamReader(
                JsonLoader.class.getClassLoader().getResourceAsStream("add_spell/items/trigger_data.json"),
                StandardCharsets.UTF_8
        )) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray items = root.getAsJsonArray("items");

            for (JsonElement element : items) {
                JsonObject obj = element.getAsJsonObject();

                String itemName = obj.get("item").getAsString();
                String mode = obj.get("mode").getAsString();
                int radius = obj.get("radius").getAsInt();
                int maxDistance = obj.get("maxDistance").getAsInt();

                JsonArray dirsArray = obj.getAsJsonArray("connectableDirections");
                List<String> dirs = new ArrayList<>();
                for (JsonElement e : dirsArray) dirs.add(e.getAsString());

                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));

                if (item == null) {
                    System.out.println("アイテムが見つかりません: " + itemName);
                    continue;
                }

                SpellNode.TriggerData data = new SpellNode.TriggerData(
                        item,
                        mode,
                        radius,
                        maxDistance,
                        dirs
                );

                TRIGGER_DATA.put(item, data);
                System.out.println("読み込み成功: " + itemName);
            }

        } catch (Exception e) {
            System.err.println("Failed to load trigger JSON: " + loc);
            e.printStackTrace();
        }
    }


    public static SpellNode.TriggerData getTriggerData(ResourceLocation key) {
        if (key == null) return null;
        return TRIGGER_DATA.get(key); // TRIGGER_DATA は Map<ResourceLocation, TriggerData>
    }

    public static void loadTriggers(JsonObject json) {
        JsonArray items = json.getAsJsonArray("items");

        for (JsonElement e : items) {
            JsonObject obj = e.getAsJsonObject();
            String itemName = obj.get("item").getAsString();
            int range = obj.get("range").getAsInt();
            String type = obj.get("type").getAsString();

            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
            if (item == null) continue;

        }
    }
}
