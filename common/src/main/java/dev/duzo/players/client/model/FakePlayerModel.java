package dev.duzo.players.client.model;

import dev.duzo.players.entities.FakePlayerEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

public class FakePlayerModel extends PlayerModel<FakePlayerEntity> {
	public FakePlayerModel(ModelPart part, boolean slim) {
		super(part, slim);
	}

	@Override
	public void setupAnim(FakePlayerEntity entity, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_) {
		super.setupAnim(entity, p_102867_, p_102868_, p_102869_, p_102870_, p_102871_);

		if (entity.isSitting() && !this.riding) {
			this.translateSitting();
		}
	}

	private void translateSitting() {
		this.rightArm.xRot += (-(float) Math.PI / 5F);
		this.rightSleeve.xRot += (-(float) Math.PI / 5F);

		this.leftArm.xRot += (-(float) Math.PI / 5F);
		this.leftSleeve.xRot += (-(float) Math.PI / 5F);

		this.rightLeg.xRot = -1.4137167F;
		this.rightLeg.yRot = ((float) Math.PI / 10F);
		this.rightLeg.zRot = 0.07853982F;
		this.rightPants.xRot = -1.4137167F;
		this.rightPants.yRot = ((float) Math.PI / 10F);
		this.rightPants.zRot = 0.07853982F;

		this.leftLeg.xRot = -1.4137167F;
		this.leftLeg.yRot = (-(float) Math.PI / 10F);
		this.leftLeg.zRot = -0.07853982F;
		this.leftPants.xRot = -1.4137167F;
		this.leftPants.yRot = (-(float) Math.PI / 10F);
		this.leftPants.zRot = -0.07853982F;
	}
}
