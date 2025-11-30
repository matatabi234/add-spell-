package com.matatabi.add_spell;

import com.matatabi.add_spell.items.ModItems;
import com.matatabi.add_spell.json.SpellDataLoader;
import com.matatabi.add_spell.menu.ModMenus;
import com.matatabi.add_spell.network.ModNetworking;
import com.matatabi.add_spell.screen.SpellBookScreen;
import com.matatabi.add_spell.tab.ModTabs;
import com.matatabi.add_spell.command.SpellDataCommand; // コマンドのインポート

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge; // MinecraftForge.EVENT_BUS のインポート
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent; // コマンドイベントのインポート
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
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public MainClass(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModMenus.MENUS.register(bus);
        ModNetworking.register();
        ModTabs.MOD_TABS.register(bus);

        // 💡 修正 1: FMLCommonSetupEvent のリスナー登録 (CommonModSetup::onCommonSetupが実行される)
        bus.addListener(CommonModSetup::onCommonSetup);

        // 💡 修正 2: コマンド登録イベントのリスナーを MinecraftForge.EVENT_BUS に登録
        MinecraftForge.EVENT_BUS.addListener(MainClass::onRegisterCommands);

        // 💡 修正 3: リロードリスナーの登録（SpellLoaderが有効な場合）
        // SpellLoaderがコメントアウトされているため、ここでは一時的にコメントアウトを維持
        // MinecraftForge.EVENT_BUS.addListener(this::addReloadListener);
    }

    // SpellLoaderが有効な場合に必要
    /*
    private void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new SpellLoader());
    }
    */


    @Mod.EventBusSubscriber(modid = MainClass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModSetup {

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            // event.enqueueWork() を使用して、非同期で安全に処理をキューに入れます。
            event.enqueueWork(() -> {
                SpellDataLoader.loadAllItemData();
                System.out.println("DEBUG: SpellDataLoader.loadAllItemData() を実行しました。");
            });
        }
    }

    /**
     * コマンド登録イベントハンドラ (MinecraftForge.EVENT_BUS に登録)
     */
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        SpellDataCommand.register(event.getDispatcher());
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