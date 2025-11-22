package com.matatabi.add_spell;

import com.matatabi.add_spell.item.ModItems;
import com.matatabi.add_spell.menu.ModMenus;
import com.matatabi.add_spell.screen.SpellBookScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("add_spell")
public class MainClass {
    public static final String MOD_ID = "add_spell";

    public static ResourceLocation makeId(String path) {
        // 💡 内部でMOD_IDを使っている
        return new ResourceLocation(MOD_ID, path);
    }

    public MainClass(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(bus);
        ModMenus.MENUS.register(bus);

    }

    @Mod.EventBusSubscriber(modid = MainClass.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientSetup {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenus.SPELLBOOK_MENU.get(), SpellBookScreen::new);
        }
    }
}
