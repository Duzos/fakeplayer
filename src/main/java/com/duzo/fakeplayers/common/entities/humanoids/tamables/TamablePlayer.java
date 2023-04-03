package com.duzo.fakeplayers.common.entities.humanoids.tamables;

import com.duzo.fakeplayers.common.entities.TamableHumanoid;
import com.duzo.fakeplayers.common.goals.MoveTowardsItemsGoal;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class TamablePlayer extends TamableHumanoid {
    public TamablePlayer(EntityType<? extends TamableHumanoid> entityType, Level level) {
        super(entityType, level);
        this.setCanPickUpLoot(true);
    }

    public TamablePlayer(EntityType<? extends TamableHumanoid> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
        this.setCanPickUpLoot(true);
    }

    public TamablePlayer(EntityType<? extends TamableHumanoid> entityType, Level level, String customName) {
        super(entityType, level, customName);
        this.setCanPickUpLoot(true);
    }

    public TamablePlayer(EntityType<? extends TamableHumanoid> entityType, Level level, ResourceLocation skin) {
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
        if (this.level.isClientSide) {
            SkinGrabber.downloadSkinFromUsername(SkinGrabber.formatEntityCustomName(this), new File(SkinGrabber.DEFAULT_DIR));
            SkinGrabber.addEntityToList(this);
        }
        //this.skin = SkinGrabber.fileToLocation(new File(SkinGrabber.DEFAULT_DIR + this.getName().getString().toLowerCase().replace(" ", "") + ".png"));
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }
}
