package mc.duzo.fakeplayer.entity;


import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import mc.duzo.fakeplayer.network.FPNetwork;
import mc.duzo.fakeplayer.network.s2c.SendSkinS2CPacket;
import mc.duzo.fakeplayer.util.SkinGrabber;

public class FakePlayerEntity extends HumanoidEntity {
    private static final TrackedData<String> SKIN_URL = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Boolean> SLIM = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HAS_NAMETAG = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FakePlayerEntity(EntityType<? extends HumanoidEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(SKIN_URL,"");
        this.dataTracker.startTracking(SLIM, false);
        this.dataTracker.startTracking(HAS_NAMETAG, true);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    private void setSlim(boolean slim) {
        this.dataTracker.set(SLIM, slim);
    }
    public boolean isSlim() {
        return this.dataTracker.get(SLIM);
    }

    private void setURL(String url, boolean sync) {
        this.dataTracker.set(SKIN_URL, url);

        if (sync) this.syncURL();
    }
    public String getURL() {
        return this.dataTracker.get(SKIN_URL);
    }
    private void syncURL() {
        if (this.getWorld().isClient()) return;

        FPNetwork.CHANNEL.sendToPlayers((Iterable<ServerPlayerEntity>) this.getWorld().getPlayers(), new SendSkinS2CPacket(this));
    }
    private void syncURL(ServerPlayerEntity target) {
        if (this.getWorld().isClient()) return;

        FPNetwork.CHANNEL.sendToPlayer(target, new SendSkinS2CPacket(this));
    }

    private void setTagVisible(boolean var) {
        this.dataTracker.set(HAS_NAMETAG, var);
    }
    public boolean isTagVisible() {
        return this.dataTracker.get(HAS_NAMETAG);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);

        if (this.getWorld().isClient()) return;
        if (name == null) return;
        if (name.getString().isBlank()) return;

        this.setURL(SkinGrabber.URL + name.getString(), true);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.setURL(nbt.getString("SkinURL"), true);
        this.setSlim(nbt.getBoolean("Slim"));
        this.setTagVisible(nbt.getBoolean("HasNameTag"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("SkinURL", this.getURL());
        nbt.putBoolean("Slim", this.isSlim());
        nbt.putBoolean("HasNameTag", this.isTagVisible());
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isIn(ItemTags.STAIRS)) {
            // toggle sitting
            this.setSitting(!this.isSitting());
            return ActionResult.SUCCESS;
        }

        if (stack.isOf(Items.NAME_TAG) && !stack.hasCustomName()) { // blank name tag
            // toggle name tag
            this.setTagVisible(!this.isTagVisible());
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);

        this.syncURL(player);
    }
}
