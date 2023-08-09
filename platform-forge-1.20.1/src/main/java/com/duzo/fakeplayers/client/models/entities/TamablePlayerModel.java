package com.duzo.fakeplayers.client.models.entities;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.humanoids.tamables.TamablePlayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class TamablePlayerModel extends PlayerModel<TamablePlayer> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "tamable_player"),"main");
    public TamablePlayerModel(ModelPart p_170821_, boolean slim) {
        super(p_170821_, slim);
    }
}
