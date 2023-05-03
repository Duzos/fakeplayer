package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerSlimEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class FakePlayerSlimEntityModel extends PlayerModel<FakePlayerSlimEntity>  {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "fake_player_slim"),"main");
    public ModelPart cloak;
    public ModelPart ear;
    public FakePlayerSlimEntityModel(ModelPart p_170821_) {
        super(p_170821_, true);
        this.ear = p_170821_.getChild("ear");
        this.cloak = p_170821_.getChild("cloak");
    }
}
