package com.duzo.fakeplayers.client;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.client.models.renderers.HumanoidEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.spongepowered.asm.mixin.injection.selectors.ElementNode;

public class FakeplayersClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @java.lang.Override
    public void onInitializeClient() {
        // Renderers
        EntityRendererRegistry.register(Fakeplayers.HUMANOID, HumanoidEntityRenderer::new);

        // Layers
    }
}
