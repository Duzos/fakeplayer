package dev.duzo.players.api;

import dev.duzo.players.entities.FakePlayerEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InteractionRegistry {
	public static final InteractionRegistry INSTANCE = new InteractionRegistry();
	private final HashMap<Item, Interaction> lookup = new HashMap<>();

	private InteractionRegistry() {
		this.defaults();
	}

	private static Set<Item> fromKey(TagKey<Item> key) {
		Set<Item> result = new HashSet<>();

		for (Item item : BuiltInRegistries.ITEM) {
			if (item.getDefaultInstance().is(key)) {
				result.add(item);
			}
		}

		return result;
	}

	private void defaults() {
		register(Items.OBSERVER, (player, entity) -> {
			// toggle ai
			entity.setNoAi(!entity.isNoAi());
			player.sendSystemMessage(Component.literal(entity.isNoAi() ? "AI Disabled" : "AI Enabled"), true);
			return InteractionResult.SUCCESS;
		});

		register(ItemTags.STAIRS, ((player, entity) -> {
			// toggle sitting
			entity.setPhysicalState(entity.isSitting() ? FakePlayerEntity.PhysicalState.STANDING : FakePlayerEntity.PhysicalState.SITTING);
			player.sendSystemMessage(Component.literal(entity.isSitting() ? "Sitting" : "Standing"), true);

			return InteractionResult.SUCCESS;
		}));

		register(ItemTags.BEDS, ((player, entity) -> {
			// toggle sleeping
			entity.setPhysicalState(entity.getPhysicalState() == FakePlayerEntity.PhysicalState.LAYING ? FakePlayerEntity.PhysicalState.STANDING : FakePlayerEntity.PhysicalState.LAYING);
			player.sendSystemMessage(Component.literal(entity.getPhysicalState() == FakePlayerEntity.PhysicalState.LAYING ? "Laying" : "Standing"), true);

			return InteractionResult.SUCCESS;
		}));

		register(ItemTags.SLABS, ((player, entity) -> {
			// toggle slim
			entity.setSlim(!entity.isSlim());
			player.sendSystemMessage(Component.literal(entity.isSlim() ? "Slim" : "Normal"), true);

			return InteractionResult.SUCCESS;
		}));

		register(Items.PAPER, ((player, entity) -> {
			// toggle name visibility
			entity.setCustomNameVisible(!entity.isCustomNameVisible());
			player.sendSystemMessage(Component.literal(entity.isCustomNameVisible() ? "Name Visible" : "Name Hidden"), true);

			return InteractionResult.SUCCESS;
		}));


	}

	private Interaction fallback() {
		return (player, entity) -> InteractionResult.PASS;
	}

	public void register(Item item, Interaction interaction) {
		lookup.put(item, interaction);
	}

	public void register(TagKey<Item> tag, Interaction interaction) {
		for (Item item : fromKey(tag)) {
			register(item, interaction);
		}
	}

	public Interaction get(Item item) {
		return lookup.getOrDefault(item, fallback());
	}

	public interface Interaction {
		InteractionResult run(ServerPlayer player, FakePlayerEntity entity);
	}
}
