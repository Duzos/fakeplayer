package mc.duzo.fakeplayer.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

import mc.duzo.fakeplayer.entity.HumanoidEntity;

public class HumanoidEntityModel extends PlayerEntityModel<HumanoidEntity> {
    public HumanoidEntityModel(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    @Override
    public void setAngles(HumanoidEntity livingEntity, float f, float g, float h, float i, float j) {
        super.setAngles(livingEntity, f, g, h, i, j);

        if (livingEntity.isSitting() && !this.riding) {
            this.rightArm.pitch += (float) (-Math.PI / 5);
            this.leftArm.pitch += (float) (-Math.PI / 5);
            this.rightLeg.pitch = -1.4137167F;
            this.rightLeg.yaw = (float) (Math.PI / 10);
            this.rightLeg.roll = 0.07853982F;
            this.leftLeg.pitch = -1.4137167F;
            this.leftLeg.yaw = (float) (-Math.PI / 10);
            this.leftLeg.roll = -0.07853982F;

            this.leftSleeve.pitch = this.leftArm.pitch;
            this.rightSleeve.pitch = this.rightArm.pitch;

            this.leftPants.pitch = this.leftLeg.pitch;
            this.rightPants.pitch = this.rightLeg.pitch;

            this.leftPants.yaw = this.leftLeg.yaw;
            this.rightPants.yaw = this.rightLeg.yaw;

            this.leftPants.roll = this.leftLeg.roll;
            this.rightPants.roll = this.rightLeg.roll;
        }
    }
}