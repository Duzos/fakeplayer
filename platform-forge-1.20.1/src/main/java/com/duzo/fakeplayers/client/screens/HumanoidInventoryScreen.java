package com.duzo.fakeplayers.client.screens;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.containers.HumanoidInventoryContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class HumanoidInventoryScreen extends AbstractContainerScreen<HumanoidInventoryContainer> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FakePlayers.MODID,"textures/gui/humanoid_inventory_screen.png");

    public HumanoidInventoryScreen(HumanoidInventoryContainer menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
