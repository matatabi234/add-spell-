package com.matatabi.add_spell.screen;

import com.matatabi.add_spell.MainClass;
import com.matatabi.add_spell.menu.SpellBookMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {

    private static final ResourceLocation TEXTURE =
            MainClass.makeId("textures/gui/common_spell_book_gui.png");

    public SpellBookScreen(SpellBookMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.titleLabelX = 4;
        this.titleLabelY = 3;
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 94;
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x00FFC4, true);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x00FF1C, true);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

}
