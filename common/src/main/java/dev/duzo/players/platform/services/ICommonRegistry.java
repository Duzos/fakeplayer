package dev.duzo.players.platform.services;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface ICommonRegistry {
	<T extends Item> Supplier<T> registerItem(String modid, String name, Supplier<T> item);

	<T extends Entity> Supplier<EntityType<T>> registerEntity(String modid, String name, Supplier<EntityType<T>> entity);

	<T extends LivingEntity> void registerAttributes(Supplier<EntityType<T>> entity, Supplier<AttributeSupplier.Builder> attributes);
}