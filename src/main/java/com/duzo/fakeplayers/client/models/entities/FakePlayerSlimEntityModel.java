package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerSlimEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class FakePlayerSlimEntityModel extends PlayerModel<FakePlayerSlimEntity>  {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "fake_player"),"main");
    public FakePlayerSlimEntityModel(ModelPart p_170821_, boolean slim) {
        super(p_170821_, slim);
    }
}
