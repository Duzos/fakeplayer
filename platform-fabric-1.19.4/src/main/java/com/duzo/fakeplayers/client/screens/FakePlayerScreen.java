package com.duzo.fakeplayers.client.screens;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.components.MyComponents;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidChatPacket;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidFollowPlayerPacket;
import com.duzo.fakeplayers.networking.packets.C2SHumanoidToggleAiPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

public class FakePlayerScreen extends Screen {
    private final HumanoidEntity humanoid;
    private final PlayerEntity player;
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
        int l = ((this.height - this.imageHeight) / 2) + (int) (this.imageHeight * 0.05);
        int i = (this.width - this.imageWidth) / 2;
        int j = this.imageWidth - (int) ( this.imageWidth * 0.1);
        System.out.println(l);
        System.out.println(i);
        System.out.println(j);
        this.input = new EditBoxWidget(this.textRenderer, (i) + (this.imageWidth/2)  - j + (j/2),l,j, 12, Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininput").toTranslationKey()), Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininputmessage").toTranslationKey()));
        this.input.setMaxLength(100);
        // @TODO: Remove the not implemented message when the feature is implemented
        this.input.tooltip((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))));
        addDrawableChild(this.input);
        int after_input_edit_box_y = this.input.getY() + this.input.getHeight() + ((int) (this.height * 0.01));


        this.chatBox = new EditBoxWidget(this.textRenderer, (i) + (this.imageWidth/2)  - j + (j/2),after_input_edit_box_y,j, 12, Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinput").toTranslationKey()), Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinputmessage").toTranslationKey()));
        this.chatBox.setMaxLength(100);
        addDrawableChild(this.chatBox);
        int after_chat_edit_box_y = this.chatBox.getY() + this.chatBox.getHeight() + ((int) (this.height * 0.02));


        this.send = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.send").toTranslationKey()), button -> {
                    Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidChatPacket(this.chatBox.getText(), this.humanoid.getUuidAsString()));
                    this.close(); // Maybe change this out if this is annoying for players
                })
                .dimensions((i) + (this.imageWidth/2) - (98/2),after_chat_edit_box_y,98,20)
                .build();
        addDrawableChild(this.send);

        int after_send_button_y = this.send.getY() + this.send.getHeight() + ((int) (this.height * 0.05));

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
                .dimensions((i) + (this.imageWidth/2) - (23), after_send_button_y,46,20)
                .build();
        addDrawableChild(this.sitting);

        int after_sitting_button_y = this.sitting.getY() + this.sitting.getHeight();

        this.stayPut = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stayput").toTranslationKey()), button -> {
                    this.stayPut.active = false;
                    this.wander.active = true;
                    this.follow.active = true;
                    Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidToggleAiPacket(true, this.humanoid.getUuidAsString()));
                    Fakeplayers.NETWORK.clientHandle().send(new C2SHumanoidFollowPlayerPacket("", this.humanoid.getUuidAsString()));
                    this.close();
                })
                .dimensions((i) + (this.imageWidth/2) - 23, after_sitting_button_y,46,20)
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
                .dimensions((i) + (this.imageWidth/2) - (23*3), after_sitting_button_y,46,20)
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
                .dimensions((i) + (this.imageWidth/2) + (23), after_sitting_button_y,46,20)
                .build();
        addDrawableChild(this.follow);

        int after_other_buttons_y = this.follow.getY() + this.follow.getHeight() + ((int) (this.height * 0.02));

        this.confirm = ButtonWidget.builder(Text.translatable(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.confirm").toTranslationKey()), button -> {
                    player.sendMessage((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), true);
                    player.sendMessage((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red"))), false);
                    this.close();

                })
                .dimensions((i) + (this.imageWidth/2) - (98/2),after_other_buttons_y,98,20)
                // @TODO: Remove the not implemented message when the feature is implemented
                .tooltip(Tooltip.of((Text) Text.translatable(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey()).setStyle(Style.EMPTY.withColor(TextColor.parse("red")))))
                .build();
        addDrawableChild(this.confirm);
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBg(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        if (this.input != null && this.confirm != null) {
            this.input.render(matrices, mouseX, mouseY, delta);
            this.chatBox.render(matrices, mouseX, mouseY, delta);
        }
    }

    public void renderBg(MatrixStack matrices) {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        drawTexture(matrices, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
