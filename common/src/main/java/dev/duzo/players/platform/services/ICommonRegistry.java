package dev.duzo.players.platform.services;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public interface ICommonRegistry {
	<T extends Item> Supplier<T> registerItem(String modid, String name, Supplier<T> item);
}