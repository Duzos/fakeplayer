package mc.duzo.fakeplayer.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import mc.duzo.fakeplayer.FakePlayerMod;
import mc.duzo.fakeplayer.client.model.HumanoidEntityModel;
import mc.duzo.fakeplayer.entity.HumanoidEntity;

public abstract class HumanoidEntityRenderer<F extends HumanoidEntity, H extends PlayerEntityModel<HumanoidEntity>> extends LivingEntityRenderer<HumanoidEntity, HumanoidEntityModel> {
    protected HumanoidEntityRenderer(EntityRendererFactory.Context context, boolean isSlim) {
        super(context, new HumanoidEntityModel(context.getPart(EntityModelLayers.PLAYER), isSlim), 0.5f);
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
        return new Identifier(FakePlayerMod .MOD_ID,"textures/entity/humanoid/humanoid.png");
    }
}