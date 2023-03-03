package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.FakePlayers;
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
        if (entity.level.isClientSide()) {
            ResourceLocation texture;
            texture = SkinGrabber.fileToLocation(new File(SkinGrabber.DEFAULT_DIR + entity.getName().getString().toLowerCase() + ".png"));
            return texture;
        }
        return new ResourceLocation(FakePlayers.MODID,SkinGrabber.ERROR_SKIN);
    }
}

