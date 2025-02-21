package dev.duzo.players.platform;

import dev.duzo.players.Constants;
import dev.duzo.players.platform.services.ICommonRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonRegistry implements ICommonRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);
	public static final HashMap<Supplier<? extends EntityType<?>>, Supplier<AttributeSupplier.Builder>> ATTRIBUTES = new HashMap<>();

	public static void init(IEventBus bus) {
		ITEMS.register(bus);
		ENTITIES.register(bus);
	}

	@Override
	public <T extends Item> Supplier<T> registerItem(String modid, String name, Supplier<T> item) {
		return ITEMS.register(name, item);
	}

	@SubscribeEvent
	public static void createAttributes(EntityAttributeCreationEvent e) {
		ATTRIBUTES.forEach((entity, attributes) -> e.put((EntityType<? extends LivingEntity>) entity.get(), attributes.get().build()));
	}

	@Override
	public <T extends Entity> Supplier<EntityType<T>> registerEntity(String modid, String name, Supplier<EntityType<T>> entity) {
		return ENTITIES.register(name, entity);
	}

	@Override
	public <T extends LivingEntity> void registerAttributes(Supplier<EntityType<T>> entity, Supplier<AttributeSupplier.Builder> attributes) {
		ATTRIBUTES.put(entity, attributes);
	}
}
