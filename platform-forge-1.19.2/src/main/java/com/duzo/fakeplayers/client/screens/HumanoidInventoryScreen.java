//package com.duzo.fakeplayers.client.screens;
//
//import com.duzo.fakeplayers.FakePlayers;
//import com.duzo.fakeplayers.common.containers.HumanoidInventoryContainer;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//
//public class HumanoidInventoryScreen extends AbstractContainerScreen<HumanoidInventoryContainer> {
//    private static final ResourceLocation TEXTURE =
//            new ResourceLocation(FakePlayers.MODID,"textures/gui/humanoid_inventory_screen.png");
//
//    public HumanoidInventoryScreen(HumanoidInventoryContainer menu, Inventory inventory, Component component) {
//        super(menu, inventory, component);
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//    }
//
//    @Override
//    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, TEXTURE);
//        int x = (width - imageWidth) / 2;
//        int y = (height - imageHeight) / 2;
//
//        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
//    }
//
//    @Override
//    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
//        renderBackground(pPoseStack);
//        super.render(pPoseStack, mouseX, mouseY, delta);
//        renderTooltip(pPoseStack, mouseX, mouseY);
//    }
//}
