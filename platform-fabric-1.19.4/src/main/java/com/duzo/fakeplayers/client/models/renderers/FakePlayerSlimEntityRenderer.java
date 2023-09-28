package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.client.models.HumanoidEntityModel;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.FakePlayerSlimEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FakePlayerSlimEntityRenderer extends LivingEntityRenderer<HumanoidEntity, HumanoidEntityModel> {
    public FakePlayerSlimEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new HumanoidEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM), true), 0.5f);
        this.addFeature(new ArmorFeatureRenderer(this, new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR)), new ArmorEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR)), context.getModelManager()));
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
        if (entity.getWorld().isClient) {
            if (entity.getCustomName() == null ||entity.getCustomName().getString().equals("")) {
                // If the name is the default blank one, send back the error
                return SkinGrabber.ERROR_TEXTURE;
            }
            Identifier texture = SkinGrabber.getEntitySkinFromList(entity);

            if (texture == null) {
                if (entity instanceof FakePlayerEntity) {
                    ((FakePlayerEntity) entity).updateSkin();
                }
            } else {
                return texture;
            }
        }
        return SkinGrabber.ERROR_TEXTURE;
    }
}
