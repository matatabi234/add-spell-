package com.matatabi.add_spell;

import com.matatabi.add_spell.items.ModItems;
import com.matatabi.add_spell.menu.ModMenus;
import com.matatabi.add_spell.network.ModNetworking;
import com.matatabi.add_spell.screen.SpellBookScreen;
import com.matatabi.add_spell.spell.SpellLoader;
import com.matatabi.add_spell.util.JsonLoader;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("add_spell")
public class MainClass {
    public static final String MOD_ID = "add_spell";

    public static ResourceLocation makeId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public MainClass(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModMenus.MENUS.register(bus);
        ModNetworking.register();
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListener);
    }
    private void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new SpellLoader());
    }

    @Mod.EventBusSubscriber(modid = MainClass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModSetup {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            JsonLoader.loadAll();
        }
    }

    // --- GUI 登録 ---
    @Mod.EventBusSubscriber(modid = MainClass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSetup {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenus.SPELLBOOK_MENU.get(), SpellBookScreen::new);
        }
    }
}

/* 🔽🔽🔽 これを MainClass.java に追加！ 🔽🔽🔽 */

@Mod.EventBusSubscriber(modid = MainClass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
class CommonModSetup {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        JsonLoader.loadAll();
    }
}

