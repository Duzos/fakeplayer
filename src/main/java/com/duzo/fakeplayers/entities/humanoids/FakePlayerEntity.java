package com.duzo.fakeplayers.entities.humanoids;

import com.duzo.fakeplayers.entities.HumanoidEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FakePlayerEntity extends HumanoidEntity {

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName) {
        super(entityType, level, customName);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
    }
    @Override
    public void setCustomName(@Nullable Component customName) {
        super.setCustomName(customName);
        SkinGrabber.downloadSkinFromUsername(this.getName().getString().toLowerCase().replace(" ", ""),new File(SkinGrabber.DEFAULT_DIR));
        //this.skin = SkinGrabber.fileToLocation(new File(SkinGrabber.DEFAULT_DIR + this.getName().getString().toLowerCase().replace(" ", "") + ".png"));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!player.level.isClientSide()) {
            if (hand == InteractionHand.MAIN_HAND) {
                if (player.isCrouching()) {
                    this.setNoAi(!this.isNoAi());
                    player.sendSystemMessage(Component.literal(this.getName().getString() + " AI has been set to: " + !this.isNoAi()).setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
