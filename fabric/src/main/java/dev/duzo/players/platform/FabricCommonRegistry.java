package dev.duzo.players.platform;

import dev.duzo.players.platform.services.ICommonRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FabricCommonRegistry implements ICommonRegistry {

	private static <T, R extends Registry<? super T>> Supplier<T> registerSupplier(R registry, String modid, String id, Supplier<T> object) {
		final T registeredObject = Registry.register((Registry<T>) registry,
				new ResourceLocation(modid, id), object.get());

		return () -> registeredObject;
	}

	@Override
	public <T extends Item> Supplier<T> registerItem(String modid, String name, Supplier<T> item) {
		return registerSupplier(BuiltInRegistries.ITEM, modid, name, item);
	}
}
