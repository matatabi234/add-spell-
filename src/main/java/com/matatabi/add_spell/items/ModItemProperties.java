package com.matatabi.add_spell.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItemProperties {

    public static Item.Properties defaultProps() {
        return new Item.Properties().stacksTo(64);
    }

    public static Item.Properties rareProps() {
        return new Item.Properties()
                .rarity(Rarity.RARE)
                .stacksTo(64);
    }

    public static Item.Properties magicProps(Rarity rarity) {
        return new Item.Properties()
                .rarity(rarity)
                .stacksTo(64);
    }
}
