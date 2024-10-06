package mc.duzo.fakeplayer.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;

import mc.duzo.fakeplayer.Register;
import mc.duzo.fakeplayer.client.renderer.FakePlayerEntityRenderer;

public class FakePlayerModClient {
    public static void init() {
        EntityRendererRegistry.register(Register.FAKE_PLAYER, FakePlayerEntityRenderer::new);
    }
}
