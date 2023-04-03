package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.client.models.entities.HumanoidEntityModel;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.File;

public class FakePlayerRenderer extends HumanoidEntityRenderer {

    public FakePlayerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public ResourceLocation getTextureLocation(HumanoidEntity entity) {
        if (entity.level.isClientSide) {
            if (entity.getCustomName().getString().equals("")) {
                // If the name is the default blank one, send back the error
                return HumanoidEntity.ERROR_TEXTURE;
            }
            ResourceLocation texture = SkinGrabber.getEntitySkinFromList(entity);
            if (texture != null) {
                return texture;
            }
        }
        if (!entity.level.isClientSide()) {
            return HumanoidEntity.ERROR_TEXTURE;
        }
        return HumanoidEntity.ERROR_TEXTURE;
    }
}

