package com.matatabi.add_spell.items;

import com.matatabi.add_spell.MainClass;
import com.matatabi.add_spell.items.custom.CommonSpellBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, MainClass.MOD_ID);

        //====== 素材アイテム ======//
        public static final RegistryObject<Item> TEST_ITEM =
                simpleItem("test_item", Rarity.UNCOMMON);

        public static final RegistryObject<Item> AREA =
                simpleItem("area", Rarity.UNCOMMON);

        public static final RegistryObject<Item> SINGLE =
                simpleItem("single", Rarity.UNCOMMON);
//
//        public static final RegistryObject<Item> MAGIC_CORE =
//                simpleItem("magic_core", Rarity.RARE);
//

        public static final RegistryObject<Item> COMMON_SPELLBOOK =
                ITEMS.register("common_spell_book",
                        () -> new CommonSpellBookItem(new Item.Properties()
                                .stacksTo(1)
                                .rarity(Rarity.COMMON)
                        )
                );

        //====== 共通登録ヘルパー ======//
        public static RegistryObject<Item> simpleItem(String name, Rarity rarity) {
                return ITEMS.register(name,
                        () -> new Item(ModItemProperties.magicProps(rarity)));
        }
}
