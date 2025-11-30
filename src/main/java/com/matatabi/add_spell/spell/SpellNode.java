package com.matatabi.add_spell.spell;

import com.matatabi.add_spell.util.JsonLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

public class SpellNode {

    public enum NodeType { EMPTY, TRIGGER, CONNECTOR, SPELL }

    private final NodeType type;
    private ItemStack itemStack;
    private TriggerData triggerData;

    public SpellNode(NodeType type) {
        this.type = type;
        this.itemStack = ItemStack.EMPTY;
        this.triggerData = null;
    }

    public void setItem(ItemStack stack) {
        this.itemStack = stack;
        if (type == NodeType.TRIGGER && !stack.isEmpty()) {
            // JSON から安全に取得
            Item item = stack.getItem();
            if (item != null) {
                this.triggerData = JsonLoader.getTriggerData(item);
            }
        }
    }

    public TriggerData getTriggerData() { return triggerData; }

    public static class TriggerData {
        public final Item item;
        public final String mode;
        public final int radius;
        public final int maxDistance;
        public final Map<String, List<String>> connectable;

        public TriggerData(Item item, String mode, int radius, int maxDistance, Map<String, List<String>> connectable) {
            this.item = item;
            this.mode = mode;
            this.radius = radius;
            this.maxDistance = maxDistance;
            this.connectable = connectable;
        }
    }

    public NodeType getType() {
        return type;
    }

//    public void setType(NodeType type) {
//        this.type = type;
//    }
}