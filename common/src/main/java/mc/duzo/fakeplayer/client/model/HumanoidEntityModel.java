package mc.duzo.fakeplayer.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

import mc.duzo.fakeplayer.entity.HumanoidEntity;

public class HumanoidEntityModel extends PlayerEntityModel<HumanoidEntity> {
    public HumanoidEntityModel(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }
}