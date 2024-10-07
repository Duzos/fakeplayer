package mc.duzo.fakeplayer.client.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import mc.duzo.fakeplayer.client.model.HumanoidEntityModel;
import mc.duzo.fakeplayer.entity.FakePlayerEntity;
import mc.duzo.fakeplayer.entity.HumanoidEntity;
import mc.duzo.fakeplayer.util.SkinGrabber;

public class FakePlayerEntityRenderer extends HumanoidEntityRenderer<FakePlayerEntity, HumanoidEntityModel> {
    public FakePlayerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, false);
    }


    @Override
    public Identifier getTexture(HumanoidEntity entity) {
        if (entity.getCustomName() == null || entity.getCustomName().getString().isBlank()) {
            // If the name is the default blank one, send back the error
            return SkinGrabber.ERROR_TEXTURE;
        }

        Identifier texture = SkinGrabber.getEntitySkinFromList(entity);

        if (texture != null) {
            return texture;
        }

        return SkinGrabber.ERROR_TEXTURE;
    }

    @Override
    protected void renderLabelIfPresent(HumanoidEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!((FakePlayerEntity) entity).isTagVisible()) return;

        super.renderLabelIfPresent(entity, text, matrices, vertexConsumers, light);
    }
}