package com.duzo.fakeplayers.client.models.entities;// Made with Blockbench 4.6.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.entities.HumanoidEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

// This class practically already existed as PlayerModel, so im just making it extend PlayerModel instead.
public class HumanoidEntityModel extends PlayerModel<HumanoidEntity> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FakePlayers.MODID, "humanoid_entity"),"main");
	public HumanoidEntityModel(ModelPart p_170821_) {
		super(p_170821_, true);
	}
}