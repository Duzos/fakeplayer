package com.duzo.fakeplayers.common.entities.humanoids;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.goals.GoToNearestBedIfNightGoal;
import com.duzo.fakeplayers.configs.FPCommonConfigs;
import com.duzo.fakeplayers.events.FakePlayersClientEvents;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.goals.MoveTowardsItemsGoal;
import com.duzo.fakeplayers.core.init.FPItems;
import com.duzo.fakeplayers.networking.Network;
import com.duzo.fakeplayers.networking.packets.RequestServerUpdateSkinsC2SPacket;
import com.duzo.fakeplayers.networking.packets.SendImageDownloadMessageS2CPacket;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FakePlayerEntity extends HumanoidEntity {
    private static final EntityDataAccessor<String> SKIN_URL = SynchedEntityData.defineId(FakePlayerEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> IS_CHUNKY = SynchedEntityData.defineId(FakePlayerEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean forceTarget = false;
    private LivingEntity target;

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
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);

        if (!FPCommonConfigs.DOES_DROP_EGGS.get()) return;

        ItemStack egg = this.getEgg();
        egg.setHoverName(this.getCustomName());

        this.spawnAtLocation(egg);
    }

    public ItemStack getEgg() {
        return FPItems.FAKE_PLAYER_SPAWN_EGG.get().getDefaultInstance();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MoveTowardsItemsGoal(this, 1D, true));
        this.goalSelector.addGoal(1, new GoToNearestBedIfNightGoal(this, 1.1D, 8));
//        this.goalSelector.addGoal(1, new FPFollowForcedTargetGoal(this,this.target,1D,20F,25F));
        super.registerGoals();
    }

    @Override
    public void setCustomName(@Nullable Component customName) {
        super.setCustomName(customName);
        if (!this.level.isClientSide()) {
            if (customName.getString().equals("")) {return;}

            this.setURL(SkinGrabber.URL + customName.getString());
            this.updateAllClientSkins();
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
        Minecraft.getInstance().setScreen(FakePlayersClientEvents.createSkinScreen(Component.translatable("screen.fakeplayers.skin"), this,player));
    }

    public String getURL() {
        return this.entityData.get(SKIN_URL);
    }

    public void setURL(String URL) {
        this.entityData.set(SKIN_URL, URL);
    }

    public boolean isChunky() {
        return this.entityData.get(IS_CHUNKY);
    }
    public void setChunky(boolean chunky) {
        this.entityData.set(IS_CHUNKY, chunky);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("URL", this.getURL());
        nbt.putBoolean("chunky", this.isChunky());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(SKIN_URL, nbt.getString("URL"));
        this.setURL(nbt.getString("URL"));

        this.entityData.set(IS_CHUNKY, nbt.getBoolean("chunky"));
        this.setChunky(nbt.getBoolean("chunky"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_URL, "");
        this.entityData.define(IS_CHUNKY,false);
    }

    public void updateAllClientSkins() {
        Network.sendToAll(new SendImageDownloadMessageS2CPacket(this.getStringUUID(),this.getStringUUID(), new File(SkinGrabber.DEFAULT_DIR), this.getURL()));
//        ImageDownloaderThread thread = new ImageDownloaderThread(this.getStringUUID(),this.getStringUUID(),new File(SkinGrabber.DEFAULT_DIR),this.getURL());
//        thread.run();
    }

    public void requestServerUpdateSkins() {
//        Network.sendToServer(new RequestServerUpdateSkinsC2SPacket(this.getId()));
        Network.sendToServer(new RequestServerUpdateSkinsC2SPacket(this.getStringUUID(),this.getStringUUID(), new File(SkinGrabber.DEFAULT_DIR), this.getURL()));
    }

    public void setForceTargeting(boolean forceTargeting) {
        this.forceTarget = forceTargeting;
    }
    public void setForceTargeting(LivingEntity entity) {
        this.forceTarget = true;
        this.target = entity;
    }
    public void sendChat(String message) {
        if (this.level.isClientSide) return;

        this.getServer().getPlayerList().broadcastChatMessage(PlayerChatMessage.system(new ChatMessageContent(message)),this.createCommandSourceStack(), ChatType.bind(ChatType.CHAT,this));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        if (!this.level.isClientSide && this.hasCustomName() && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.dead) {
            net.minecraft.network.chat.Component deathMessage = this.getCombatTracker().getDeathMessage();
            this.getServer().getPlayerList().broadcastSystemMessage(deathMessage,false);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            if (!(this.isNoAi()) && this.forceTarget && this.target != null && !(this.getTarget() == this.target)) {
                this.getNavigation().moveTo(this.target,2D);
            }

            if (this.isChunky()) {
                System.out.println(ForgeChunkManager.forceChunk((ServerLevel) this.level, FakePlayers.MODID,this,this.blockPosition().getX(),this.getBlockZ(),true,true));
                System.out.println(level.getChunkAt(this.blockPosition()));
            }

            if (this.level.isNight() && this.level.getBlockState(this.blockPosition()).is(BlockTags.BEDS) && !this.isSleeping()) {
                this.startSleeping(this.blockPosition());
                System.out.println("starting sleep");
            } else if (this.isSleeping() && this.level.isDay()) {
                this.stopSleeping();
            }
        }

    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (player.isCrouching()) {
                if (this.level.isClientSide) {
                    this.openSkinScreen(player);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
