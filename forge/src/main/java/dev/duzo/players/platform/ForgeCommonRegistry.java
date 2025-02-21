package dev.duzo.players.platform;

import dev.duzo.players.Constants;
import dev.duzo.players.platform.services.ICommonRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ForgeCommonRegistry implements ICommonRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MOD_ID);

	public static void init(IEventBus bus) {
		ITEMS.register(bus);
	}

	@Override
	public <T extends Item> Supplier<T> registerItem(String modid, String name, Supplier<T> item) {
		return ITEMS.register(name, item);
	}
}
