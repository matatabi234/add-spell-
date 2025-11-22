package com.matatabi.add_spell.setup;

import com.matatabi.add_spell.util.JsonLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        JsonLoader.loadAll(); // ここで JSON をロード
    }
}

