package mc.duzo.fakeplayer;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import mc.duzo.fakeplayer.entity.FakePlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;


import java.util.function.Supplier;

import static mc.duzo.fakeplayer.FakePlayerMod.MOD_ID;

public class Register {
	public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

	// Registering a new creative tab
	public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM_GROUP);
	public static final RegistrySupplier<ItemGroup> FP_TAB = TABS.register("tab", () ->
			CreativeTabRegistry.create(Text.translatable("itemGroup." + MOD_ID + ".tab"),
					() -> new ItemStack(Items.IRON_INGOT)));

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MOD_ID, RegistryKeys.ENTITY_TYPE);
	public static final RegistrySupplier<EntityType<FakePlayerEntity>> FAKE_PLAYER = ENTITIES.register("fake_player", () -> EntityType.Builder
			.create(FakePlayerEntity::new, SpawnGroup.MISC)
			.setDimensions(0.6f, 1.8f)
			.build("fake_player"));

	public static void init() {
		ENTITIES.register();
		TABS.register();
		ITEMS.register();

		EntityAttributeRegistry.register(FAKE_PLAYER, FakePlayerEntity::getHumanoidAttributes);
	}
}
