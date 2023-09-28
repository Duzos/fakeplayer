package com.duzo.fakeplayers.client.screens;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.components.MyComponents;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidChatPacket;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidFollowPlayerPacket;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidToggleAiPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class FakePlayerScreen extends Screen {
    private HumanoidEntity humanoid;
    private PlayerEntity player;
    private static final Identifier GUI_TEXTURE = new Identifier(Fakeplayers.MOD_ID,"textures/gui/skin_select.png");

    protected int imageWidth = 176;
    protected int imageHeight = 166;

    private EditBoxWidget input, chatBox;
    private ButtonWidget confirm, send, stayPut, follow, wander, sitting;
    @Environment(EnvType.CLIENT)
    public FakePlayerScreen(Text title, HumanoidEntity humanoid1, PlayerEntity player1) {
        super(title);
        this.humanoid = humanoid1;
        this.player = player1;
    }

    @Override
    protected void init() {
        super.init();
        int l = this.height / 4 + 48;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageWidth) / 2;
        this.input = new EditBoxWidget(this.textRenderer, (i) + (this.imageWidth/2)  - j + (j/2),l,j, 12, Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininput").toTranslationKey()), Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininputmessage").toTranslationKey()));
        this.input.setMaxLength(100);
        // @TODO: Remove the not implemented message when the feature is implemented
        this.input.tooltip((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))));
        addDrawableChild(this.input);

        this.chatBox = new EditBoxWidget(this.textRenderer, (i) + (this.imageWidth/2)  - j + (j/2),l + 20,j, 12, Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinput").toTranslationKey()), Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinputmessage").toTranslationKey()));
        this.chatBox.setMaxLength(100);
        addDrawableChild(this.chatBox);

        this.send = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.send").toTranslationKey()), button -> {
            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidChatPacket(this.chatBox.getText(), this.humanoid.getUuidAsString()));
            this.close(); // Maybe change this out if this is annoying for players
        })
                .dimensions((i) + (this.imageWidth/2) - (98/2),l + 40,98,20)
                .build();
        addDrawableChild(this.send);
        this.confirm = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.confirm").toTranslationKey()), button -> {
            player.sendMessage((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), true);
            player.sendMessage((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), false);
            this.close();

        })
                .dimensions((i) + (this.imageWidth/2) - (98/2),l + (128),98,20)
                // @TODO: Remove the not implemented message when the feature is implemented
                .tooltip(Tooltip.of((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red")))))
                .build();
        addDrawableChild(this.confirm);
        this.sitting = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey()), button -> {
                    MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).toggleSitting();
                    this.stayPut.active = !MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).isSitting();
                    this.wander.active = true;
                    this.follow.active = true;
                    Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidToggleAiPacket(!this.stayPut.active, this.humanoid.getUuidAsString()));
                    Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidFollowPlayerPacket("", this.humanoid.getUuidAsString()));
                    this.close();
            this.close();
        })
                .dimensions((i) + (this.imageWidth/2) - (23), l + 80,46,20)
                .build();
        addDrawableChild(this.sitting);
        this.stayPut = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stayput").toTranslationKey()), button -> {
            this.stayPut.active = false;
            this.wander.active = true;
            this.follow.active = true;
            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidToggleAiPacket(true, this.humanoid.getUuidAsString()));
            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidFollowPlayerPacket("", this.humanoid.getUuidAsString()));
            this.close();
        })
                .dimensions((i) + (this.imageWidth/2) - 23, l + 100,46,20)
                .build();
        addDrawableChild(this.stayPut);
        this.wander = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.wander").toTranslationKey()), button -> {
            this.wander.active = false;
            this.stayPut.active = true;
            if (MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).isSitting()) {
                MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).toggleSitting();
            }

            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidToggleAiPacket(false, this.humanoid.getUuidAsString())); // More or less setting to default state
            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidFollowPlayerPacket("", this.humanoid.getUuidAsString()));
            this.close();
        })
                .dimensions((i) + (this.imageWidth/2) - (23*3), l + 100,46,20)
                .build();
        addDrawableChild(this.wander);
        this.follow = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.follow").toTranslationKey()), button -> {
            this.follow.active = false;
            this.stayPut.active = true;
            this.wander.active = true;
            if (MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).isSitting()) {
                MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).toggleSitting();
            }
            if (this.humanoid.isAiDisabled()) {
                Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidToggleAiPacket(false, this.humanoid.getUuidAsString()));
            }
            Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidFollowPlayerPacket(this.player.getUuidAsString(), this.humanoid.getUuidAsString()));
            this.close();
        })
                .dimensions((i) + (this.imageWidth/2) + (23), l + 100,46,20)
                .build();
        addDrawableChild(this.follow);
        assert this.player != null;
        assert this.humanoid != null;
        if (MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(this.humanoid).isSitting()) {
            this.sitting.setMessage(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stand").toTranslationKey()));
            this.stayPut.active = false;
            this.wander.active = true;
            this.follow.active = true;
        }
        else if (this.humanoid.isAiDisabled()) {
            this.sitting.setMessage(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey()));
            this.stayPut.active = false;
            this.wander.active = true;
            this.follow.active = true;
        }
        else if (this.humanoid.forcedTarget != null && this.humanoid.forcedTarget.equals(this.player)) {
            this.sitting.setMessage(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey()));
            this.stayPut.active = true;
            this.wander.active = true;
            this.follow.active = false;
        }
        else {
            this.sitting.setMessage(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey()));
            this.stayPut.active = true;
            this.wander.active = false;
            this.follow.active = true;
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBg(context);
        super.render(context, mouseX, mouseY, delta);
        if (this.input != null && this.confirm != null) {
            this.input.render(context, mouseX, mouseY, delta);
            this.chatBox.render(context, mouseX, mouseY, delta);
        }
    }

    public void renderBg(DrawContext context) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        context.drawTexture(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
