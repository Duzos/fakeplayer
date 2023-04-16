package com.duzo.fakeplayers.client.screens;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class FPSkinScreen extends Screen {
    private FakePlayerEntity humanoid;
    private EditBox input;
    private Button confirm;
    public FPSkinScreen(Component component, FakePlayerEntity humanoid) {
        super(component);
        this.humanoid = humanoid;
    }

    @Override
    protected void init() {
        super.init();
        int l = this.height / 4 + 48;

        assert this.minecraft != null;
        this.input = new EditBox(this.minecraft.fontFilterFishy, 4, this.height / 2, this.width, 12, Component.translatable("screen.fakeplayers.skin"));
        this.input.setValue(this.humanoid.getURL());
        this.input.setMaxLength(100);
        this.input.setBordered(true);
        this.addWidget(this.input);

        this.confirm = Button.builder(Component.translatable("screens.fakeplayers.done"), (p_96786_) -> {
            this.pressDoneButton();
        }).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build();
        this.addRenderableWidget(this.confirm);
    }

    private void updateEntityURL() {
        this.humanoid.setURL(this.input.getValue());
    }

    private void pressDoneButton() {
        this.updateEntityURL();
        this.humanoid.updateSkin();
        this.onClose();
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        if (this.input != null && this.confirm != null) {
            this.input.render(pPoseStack, mouseX, mouseY, delta);
            this.confirm.render(pPoseStack, mouseX, mouseY, delta);
        }

        super.render(pPoseStack, mouseX, mouseY, delta);
    }
}
