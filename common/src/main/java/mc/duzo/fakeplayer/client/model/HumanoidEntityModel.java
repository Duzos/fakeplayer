package mc.duzo.fakeplayer.client.model;

import mc.duzo.fakeplayer.entity.HumanoidEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class HumanoidEntityModel extends PlayerEntityModel<HumanoidEntity> {
	public HumanoidEntityModel(ModelPart root, boolean thinArms) {
		super(root, thinArms);
	}
}