package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.client.models.HumanoidEntityModel;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class FakePlayerEntityRenderer extends HumanoidEntityRenderer<FakePlayerEntity, HumanoidEntityModel> {
    public FakePlayerEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
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
