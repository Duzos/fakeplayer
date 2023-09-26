package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.client.models.HumanoidEntityModel;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HumanoidEntityRenderer<F extends HumanoidEntity, H extends PlayerEntityModel<HumanoidEntity>> extends LivingEntityRenderer<HumanoidEntity, HumanoidEntityModel> {
    public HumanoidEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HumanoidEntityModel(context.getPart(EntityModelLayers.PLAYER), false), 0.5f);
        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)), new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public void render(HumanoidEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.9375F, 0.9375F, 0.9375F);

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(HumanoidEntity entity) {
        return new Identifier(Fakeplayers.MOD_ID,"textures/entity/humanoid/humanoid.png");
    }
}
