package com.duzo.fakeplayers.common.entities;

import com.duzo.fakeplayers.common.goals.MoveTowardsItemsGoal;
import com.duzo.fakeplayers.components.MyComponents;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FakePlayerEntity extends HumanoidEntity{

    private static final TrackedData<String> SKIN_URL;

    static {
        SKIN_URL = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.STRING);
    }
    public FakePlayerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1,new MoveTowardsItemsGoal(this, 1D, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SKIN_URL,"");
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    private void setURL(String url) {
        MyComponents.FAKE_PLAYER_SKIN_COMPONENT_COMPONENT.get(this).setURL(url);
        this.dataTracker.set(SKIN_URL, url);
    }
    public String getURL() {
        return MyComponents.FAKE_PLAYER_SKIN_COMPONENT_COMPONENT.get(this).getURL();
    }

    public void updateSkin() {
        MyComponents.FAKE_PLAYER_SKIN_COMPONENT_COMPONENT.sync(this);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (!this.getWorld().isClient()) {
            if (name.getString().equals("")) {
                return;
            }

            this.setURL(SkinGrabber.URL + name.getString());
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND && player.isSneaking() && player.getWorld().isClient) {
            // @TODO: Replace with a better screen

            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }
}
