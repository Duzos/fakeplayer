package com.duzo.fakeplayers.common.entities.humanoids;

import com.duzo.fakeplayers.client.screens.FPSkinScreen;
import com.duzo.fakeplayers.client.threads.ImageDownloaderThread;
import com.duzo.fakeplayers.common.containers.HumanoidInventoryContainer;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.goals.MoveTowardsItemsGoal;
import com.duzo.fakeplayers.networking.Network;
import com.duzo.fakeplayers.networking.packets.SendImageDownloadMessageS2CPacket;
import com.duzo.fakeplayers.networking.packets.SendSkinMessageS2CPacket;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FakePlayerEntity extends HumanoidEntity {
    private String skinURL;

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
        this.setCanPickUpLoot(true);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName) {
        super(entityType, level, customName);
        this.setCanPickUpLoot(true);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoveTowardsItemsGoal(this, 1D, true));
        super.registerGoals();
    }

    @Override
    public void setCustomName(@Nullable Component customName) {
    super.setCustomName(customName);
    if (!this.level.isClientSide()) {
        if (customName.getString().equals("")) {return;}

        this.skinURL = SkinGrabber.URL + customName.getString();
        Network.sendToAll(new SendSkinMessageS2CPacket(customName.getString()));
    }
}

    public void toggleAI(Player player) {
        this.setNoAi(!this.isNoAi());
        player.sendSystemMessage(Component.literal(
                        this.getName().getString() + " AI has been set to: " + !this.isNoAi())
                .setStyle(Style.EMPTY
                        .withColor(ChatFormatting.LIGHT_PURPLE)));
    }

    public static void downloadAndAddSkin(String name, Entity entity) {
        SkinGrabber.downloadSkinFromUsername(name.toLowerCase().replace(" ", ""), new File(SkinGrabber.DEFAULT_DIR));
        SkinGrabber.addEntityToList(entity);
    }

    private void openSkinScreen(Player player) {
        Minecraft.getInstance().setScreen(new FPSkinScreen(Component.translatable("screen.fakeplayers.skin"), this));
    }

    public String getURL() {
        return this.skinURL;
    }

    public void setURL(String URL) {
        this.skinURL = URL;
    }

    public void updateSkin() {
        Network.sendToAll(new SendImageDownloadMessageS2CPacket(this.customName,this.getURL(), new File(SkinGrabber.DEFAULT_DIR), this.getURL()));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (player.isCrouching()) {
                this.openSkinScreen(player);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

}
