package com.duzo.fakeplayers.client;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerEntityRenderer;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerSlimEntityRenderer;
import com.duzo.fakeplayers.client.models.renderers.HumanoidEntityRenderer;
import com.duzo.fakeplayers.common.entities.FakePlayerSlimEntity;
import com.duzo.fakeplayers.network.NetworkConstants;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.networking.client.ClientPlayNetworkAddon;
import org.spongepowered.asm.mixin.injection.selectors.ElementNode;

import java.io.File;

public class FakeplayersClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @java.lang.Override
    public void onInitializeClient() {
        // Renderers
        EntityRendererRegistry.register(Fakeplayers.FAKE_PLAYER, FakePlayerEntityRenderer::new);
        EntityRendererRegistry.register(Fakeplayers.FAKE_PLAYER_SLIM, FakePlayerSlimEntityRenderer::new);

        // Layers

        // Packet
        ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.SEND_SKIN_PACKET_ID, ((client, handler, buf, responseSender) -> {
            String uuid = buf.readString();
            String filename = buf.readString();
            String filepathString = buf.readString();
            String URL = buf.readString();
            File filepath = new File(filepathString);

            SkinGrabber.downloadImageFromURL(filename.toLowerCase().replace(" ", ""), filepath, URL);
            SkinGrabber.addCustomNameToList(uuid);
        }));
    }
}
