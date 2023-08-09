package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.humanoids.tamables.TamablePlayerSlim;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class TamablePlayerSlimModel extends PlayerModel<TamablePlayerSlim> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "tamable_player_slim"), "main");
    public ModelPart cloak;
    public ModelPart ear;

    public TamablePlayerSlimModel(ModelPart p_170821_) {
        super(p_170821_, true);
        this.ear = p_170821_.getChild("ear");
        this.cloak = p_170821_.getChild("cloak");
    }
}
