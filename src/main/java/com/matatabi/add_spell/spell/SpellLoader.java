package com.matatabi.add_spell.spell;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class SpellLoader extends SimpleJsonResourceReloadListener {

    public static final Map<String, SpellData> SPELLS = new HashMap<>();

    public SpellLoader() {
        super(new Gson(), "spells");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
        SPELLS.clear();

        for (var entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonObject json = entry.getValue().getAsJsonObject();

            String name = json.get("name").getAsString();
            String targetType = json.get("target_type").getAsString();
            int range = json.get("range").getAsInt();
            int power = json.get("power").getAsInt();

            SPELLS.put(id.getPath(), new SpellData(name, targetType, range, power));
        }
    }
}

