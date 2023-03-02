package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.client.models.entities.HumanoidEntityModel;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class HumanoidEntityRenderer extends LivingEntityRenderer<HumanoidEntity, HumanoidEntityModel> {
    public HumanoidEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidEntityModel(Minecraft.getInstance().getEntityModels().bakeLayer(HumanoidEntityModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        // @TODO ARMOUR RENDERING - "Cannot find ears!"
        //this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidEntityModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)), new HumanoidEntityModel(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    @Override
    public void render(HumanoidEntity entity, float p_115456_, float p_115457_, PoseStack matrixStack, MultiBufferSource p_115459_, int p_115460_) {
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
    protected boolean shouldShowName(HumanoidEntity entity) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidEntity entity) {
        return entity.skin;
    }
}
