package com.duzo.fakeplayers.client.models.renderers;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.entities.HumanoidEntity;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

public class FakePlayerRenderer extends HumanoidEntityRenderer {

    public FakePlayerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public ResourceLocation getTextureLocation(HumanoidEntity entity) {
        ResourceLocation texture;
        texture = SkinGrabber.fileToLocation(new File(SkinGrabber.DEFAULT_DIR + entity.getName().getString().toLowerCase() + ".png"));
        return texture;
    }
}

