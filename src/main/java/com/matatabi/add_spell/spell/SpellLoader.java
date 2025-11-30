//package com.matatabi.add_spell.spell;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.matatabi.add_spell.menu.SpellContainer;
//import com.matatabi.add_spell.util.JsonLoader;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.nbt.ListTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.packs.resources.ResourceManager;
//import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
//import net.minecraft.util.profiling.ProfilerFiller;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class SpellLoader extends SimpleJsonResourceReloadListener {
//
//    public static final Map<String, SpellData> SPELLS = new HashMap<>();
//
//    public SpellLoader() {
//        super(new Gson(), "items"); // data/add_spell/items/*.json を読む
//    }
//
//    @Override
//    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager manager, ProfilerFiller profiler) {
//        JsonLoader.TRIGGER_DATA.clear();
//
//        for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
//            JsonObject root = entry.getValue().getAsJsonObject();
//            // JSON に items 配列が存在する場合のみ処理
//            if (!root.has("items")) continue;
//            JsonArray items = root.getAsJsonArray("items");
//
//            for (JsonElement e : items) {
//                JsonObject obj = e.getAsJsonObject();
//
//                String itemName = obj.get("item").getAsString();
//                String mode = obj.get("mode").getAsString();
//                int radius = obj.get("radius").getAsInt();
//                int maxDistance = obj.get("maxDistance").getAsInt();
//
//                // "connectableDirections" は削除
//                // List<String> dirs = new ArrayList<>();
//
//                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
//                if (item == null) {
//                    System.out.println("アイテムが見つからない: " + itemName);
//                    continue;
//                }
//
//                // connectable を読み込む
//                JsonObject connectableObj = obj.getAsJsonObject("connectable");
//                Map<String, List<String>> connectableMap = new HashMap<>();
//                for (String key : connectableObj.keySet()) {
//                    JsonArray arr = connectableObj.getAsJsonArray(key);
//                    List<String> list = new ArrayList<>();
//                    for (JsonElement elem : arr) list.add(elem.getAsString());
//                    connectableMap.put(key, list);
//                }
//
//                SpellNode.TriggerData dataObj = new SpellNode.TriggerData(
//                        item, mode, radius, maxDistance, connectableMap
//                );
//
//                JsonLoader.TRIGGER_DATA.put(item, dataObj);
//                System.out.println("読み込み成功: " + itemName);
//            }
//        }
//    }
//
//    public static void saveToItem(ItemStack stack, SpellContainer spellContainer) {
//        CompoundTag tag = stack.getOrCreateTag();
//        ListTag items = new ListTag();
//
//        for (int i = 0; i < spellContainer.getContainerSize(); i++) {
//            items.add(spellContainer.getItem(i).save(new CompoundTag()));
//        }
//
//        tag.put("SpellItems", items);
//
//        // 有効フラグも保存
//        tag.putBoolean("SpellValid", spellContainer.isValid());
//    }
//
//
//}
//
