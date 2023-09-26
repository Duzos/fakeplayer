package com.duzo.fakeplayers.client.gui;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.FakePlayerSlimEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.itemgroup.FabricCreativeGuiComponents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.ScreenNarrator;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FakePlayerEntityScreen extends Screen {
    private static final Identifier GUI_TEXTURE = new Identifier(Fakeplayers.MOD_ID,"textures/gui/skin_select.png");

    public FakePlayerEntityScreen(FakePlayerEntity fakePlayer) {
        super(Text.translatable("screens.fakeplayers"));
        this.fakePlayer = fakePlayer;
    }
    private EditBoxWidget url,chat;
    private ButtonWidget confirm,send;
    private final FakePlayerEntity fakePlayer;
    protected final int imageWidth = 176;
    protected final int imageHeight = 166;

    @Override
    protected void init() {
        super.init();
        int l = this.height / 4 + 48;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageWidth) / 2;

        this.confirm = ButtonWidget.builder(Text.translatable("screens.fakeplayers.done"), button -> {
            System.out.println("Confirmed");
        }).dimensions((i) + (this.imageWidth/2) - (98/2),l + (128),98,20).build();

        this.url = new EditBoxWidget(this.textRenderer,((this.width - this.imageWidth) / 2) + (this.imageWidth/2)  - (this.height - this.imageWidth) / 2 + ((this.height - this.imageWidth) / 2/2),this.height / 4 + 48,(this.height - this.imageWidth) / 2, 12, Text.translatable("screen.fakeplayers.skin"),Text.translatable("screen.fakeplayers.skin"));

        addDrawableChild(this.url);
        addDrawableChild(this.confirm);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBg(context);
        super.render(context, mouseX, mouseY, delta);
    }

    public void renderBg(DrawContext context) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        context.drawTexture(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
