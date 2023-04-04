package com.duzo.fakeplayers.common.entities.humanoids;

import com.duzo.fakeplayers.common.containers.HumanoidInventoryContainer;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.goals.MoveTowardsItemsGoal;
import com.duzo.fakeplayers.networking.Network;
import com.duzo.fakeplayers.networking.packets.SendSkinMessageS2CPacket;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.ChatFormatting;
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
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FakePlayerEntity extends HumanoidEntity {

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
            if (customName.getString().equals("")) {
//                System.out.println("Packet cancelled due to blank name");
                return;
            }
//            System.out.println("Sending packet with string : " + customName.getString());
            Network.sendToAll(new SendSkinMessageS2CPacket(customName.getString()));
        }
}

    public static void downloadAndAddSkin(String name, Entity entity) {
        System.out.println(name);
        System.out.println(entity);
        SkinGrabber.downloadSkinFromUsername(name.toLowerCase().replace(" ", ""), new File(SkinGrabber.DEFAULT_DIR));
        SkinGrabber.addEntityToList(entity);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!player.level.isClientSide()) {
            if (hand == InteractionHand.MAIN_HAND) {
                if (player.isCrouching()) {
                    this.setNoAi(!this.isNoAi());
                    player.sendSystemMessage(Component.literal(
                            this.getName().getString() + " AI has been set to: " + !this.isNoAi())
                            .setStyle(Style.EMPTY
                                    .withColor(ChatFormatting.LIGHT_PURPLE)));
                    return InteractionResult.SUCCESS;
//                    player.openMenu(createContainerProvider(this));
                }
            }
        }
        return InteractionResult.FAIL;
    }

    private MenuProvider createContainerProvider(HumanoidEntity entity) {
        return new MenuProvider() {

            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
                return new HumanoidInventoryContainer(i, playerInventory, entity);
            }

            public Component getDisplayName() {
                return Component.translatable("screen.fakeplayers.inventory_gui");
            }
        };
    }
}
