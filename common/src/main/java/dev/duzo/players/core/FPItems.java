package dev.duzo.players.core;

import dev.duzo.players.Constants;
import dev.duzo.players.platform.Services;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class FPItems {
	public static final Supplier<Item> PLAYER_AI = register("player_ai", () -> new Item(new Item.Properties()));
	public static final Supplier<Item> PLAYER_SHELL = register("player_shell", () -> new Item(new Item.Properties()));

	public static Supplier<Item> register(String name, Supplier<Item> item) {
		return Services.COMMON_REGISTRY.registerItem(Constants.MOD_ID, name, item);
	}

	public static void init() {

	}
}
