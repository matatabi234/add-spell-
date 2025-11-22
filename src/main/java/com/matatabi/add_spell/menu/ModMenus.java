package com.matatabi.add_spell.menu;

import com.matatabi.add_spell.MainClass;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, MainClass.MOD_ID);

    public static final RegistryObject<MenuType<SpellBookMenu>> SPELLBOOK_MENU =
            MENUS.register("spellbook_menu",
                    () -> new MenuType<>(
                            new IContainerFactory<SpellBookMenu>() {
                                @Override
                                public SpellBookMenu create(int id, Inventory inv, FriendlyByteBuf buf) {
                                    return new SpellBookMenu(id, inv, buf.readItem());
                                }
                            },
                            FeatureFlagSet.of()
                    )
            );


    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
