package com.duzo.fakeplayers.components;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.util.Identifier;

public class MyComponents implements EntityComponentInitializer {
    public static final ComponentKey<FakePlayerSkinComponent> FAKE_PLAYER_SKIN_COMPONENT_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Fakeplayers.MOD_ID, "fake_player_skin_component"), FakePlayerSkinComponent.class);
    public static final ComponentKey<FakePlayerSittingComponent> FAKE_PLAYER_SITTING_COMPONENT_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Fakeplayers.MOD_ID, "fake_player_sitting_component"), FakePlayerSittingComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(HumanoidEntity.class, FAKE_PLAYER_SKIN_COMPONENT_COMPONENT, FakePlayerSkinComponent::new);
        registry.registerFor(HumanoidEntity.class, FAKE_PLAYER_SITTING_COMPONENT_COMPONENT, FakePlayerSittingComponent::new);
    }
}
