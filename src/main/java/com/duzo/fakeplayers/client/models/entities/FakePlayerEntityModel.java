package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.entities.HumanoidEntity;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class FakePlayerEntityModel extends PlayerModel<FakePlayerEntity>  {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "fake_player"),"main");
    public FakePlayerEntityModel(ModelPart p_170821_, boolean slim) {
        super(p_170821_, slim);
    }
}
