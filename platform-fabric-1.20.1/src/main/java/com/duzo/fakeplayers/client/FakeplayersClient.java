package com.duzo.fakeplayers.client;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerEntityRenderer;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerSlimEntityRenderer;
import com.duzo.fakeplayers.networking.packets.S2CDownloadSkinAsync;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import java.io.File;
import java.util.Arrays;

public class FakeplayersClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        // Renderers
        EntityRendererRegistry.register(Fakeplayers.FAKE_PLAYER, FakePlayerEntityRenderer::new);
        EntityRendererRegistry.register(Fakeplayers.FAKE_PLAYER_SLIM, FakePlayerSlimEntityRenderer::new);
        Fakeplayers.NETWORK.registerClientbound(S2CDownloadSkinAsync.class, ((message, access) -> {
            String[] urls = message.urls();
            for (String url : urls) {
                SkinGrabber.downloadImageFromURLAsync(url);
            }

        }));
    }
}
