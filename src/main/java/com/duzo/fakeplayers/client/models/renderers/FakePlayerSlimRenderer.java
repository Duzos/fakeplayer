package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.client.models.entities.FakePlayerSlimEntityModel;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerSlimEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.io.File;

public class FakePlayerSlimRenderer extends LivingEntityRenderer<FakePlayerSlimEntity, FakePlayerSlimEntityModel> {
    public FakePlayerSlimRenderer(EntityRendererProvider.Context context) {
        super(context, new FakePlayerSlimEntityModel(Minecraft.getInstance().getEntityModels().bakeLayer(FakePlayerSlimEntityModel.LAYER_LOCATION),true), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        // @TODO ARMOUR RENDERING - "Cannot find ears!"
        //this.addLayer(new HumanoidArmorLayer<>(this, new FakePlayerSlimEntityModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)), new FakePlayerSlimEntityModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }
    


    @Override
    public void render(FakePlayerSlimEntity entity, float p_115456_, float p_115457_, PoseStack matrixStack, MultiBufferSource p_115459_, int p_115460_) {
        matrixStack.pushPose();
        if (entity.isBaby()) {
            matrixStack.scale(0.5f,0.5f,0.5f);
        } else {
            matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
        }
        super.render(entity, p_115456_, p_115457_, matrixStack, p_115459_, p_115460_);
        matrixStack.popPose();
    }

    @Override
    protected boolean shouldShowName(FakePlayerSlimEntity entity) {
        return true;
    }
    @Override
    public ResourceLocation getTextureLocation(FakePlayerSlimEntity entity) {
        ResourceLocation texture;
        texture = SkinGrabber.fileToLocation(new File(SkinGrabber.DEFAULT_DIR + entity.getName().getString().toLowerCase() + ".png"));
        return texture;
    }
}

