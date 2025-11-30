package com.matatabi.add_spell.tab;

import com.matatabi.add_spell.MainClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {

    public static final DeferredRegister<CreativeModeTab> MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainClass.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ADD_SPELL_TAB = MOD_TABS.register("add_spell_tab",
            ()-> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Blocks.STONE))
                    .title(Component.translatable("itemGroup.add_spell"))
                    .displayItems((param,output)->{
                        for (Item item:CreateTab.items){
                            output.accept(item);
                        }
                    })
                    .build()
    );
}
