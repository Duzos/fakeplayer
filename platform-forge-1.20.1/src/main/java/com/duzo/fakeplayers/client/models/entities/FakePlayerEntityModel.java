package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class FakePlayerEntityModel extends PlayerModel<FakePlayerEntity>  {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "fake_player"),"main");
    public FakePlayerEntityModel(ModelPart p_170821_) {
        super(p_170821_, false);
    }

    @Override
    public void setupAnim(FakePlayerEntity entity, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
        super.setupAnim(entity, p_102867_, p_102868_, p_102869_, p_102870_, p_102871_);

        if (entity.isSitting() && !this.riding) {
            this.rightArm.xRot += (-(float)Math.PI / 5F);
            this.rightSleeve.xRot += (-(float)Math.PI / 5F);

            this.leftArm.xRot += (-(float)Math.PI / 5F);
            this.leftSleeve.xRot += (-(float)Math.PI / 5F);

            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.zRot = 0.07853982F;
            this.rightPants.xRot = -1.4137167F;
            this.rightPants.yRot = ((float)Math.PI / 10F);
            this.rightPants.zRot = 0.07853982F;

            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.zRot = -0.07853982F;
            this.leftPants.xRot = -1.4137167F;
            this.leftPants.yRot = (-(float)Math.PI / 10F);
            this.leftPants.zRot = -0.07853982F;
        }
    }
}
