package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.client.models.entities.TamablePlayerModel;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.entities.humanoids.tamables.TamablePlayer;
import com.duzo.fakeplayers.util.SkinGrabber;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class TamablePlayerRenderer extends LivingEntityRenderer<TamablePlayer, TamablePlayerModel> {
    public TamablePlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new TamablePlayerModel(Minecraft.getInstance().getEntityModels().bakeLayer(TamablePlayerModel.LAYER_LOCATION), false), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),context.getModelManager()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    @Override
    public void render(TamablePlayer entity, float p_115456_, float p_115457_, PoseStack matrixStack, MultiBufferSource p_115459_, int p_115460_) {
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
    protected boolean shouldShowName(TamablePlayer entity) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(TamablePlayer entity) {
        if (entity.level().isClientSide) {
            if (entity.getCustomName().getString().equals("")) {
                // If the name is the default blank one, send back the error
                return HumanoidEntity.ERROR_TEXTURE;
            }
            ResourceLocation texture = SkinGrabber.getEntitySkinFromList(entity);
            if (texture != null) {
                return texture;
            }
        }
        if (!entity.level().isClientSide()) {
            return HumanoidEntity.ERROR_TEXTURE;
        }
        return HumanoidEntity.ERROR_TEXTURE;
    }
}
