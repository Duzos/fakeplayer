package com.duzo.fakeplayers.datagen;


import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.ItemInit;
import com.duzo.fakeplayers.datagen.datagen_providers.ModLanguageProvider;
import com.duzo.fakeplayers.datagen.datagen_providers.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		generateLanguages(pack);
		generateRecipes(pack);
	}

	public void generateRecipes(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> {
			ModRecipeProvider modRecipeProvider = new ModRecipeProvider(output);
			modRecipeProvider.addShapedRecipe(ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.PLAYER_AI, 1)
					.pattern("iLi")
					.pattern("iOi")
					.pattern("iRi")
					.input('i', Items.IRON_INGOT)
					.input('L', Items.LIME_DYE)
					.input('O', Items.OBSERVER)
					.input('R', Items.REDSTONE_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(Items.OBSERVER), FabricRecipeProvider.conditionsFromItem(Items.OBSERVER))
					.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
					.criterion(FabricRecipeProvider.hasItem(Items.LIME_DYE), FabricRecipeProvider.conditionsFromItem(Items.LIME_DYE))
			);

			modRecipeProvider.addShapedRecipe(ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.PLAYER_SHELL, 1)
					.pattern("iCi")
					.pattern("CRC")
					.pattern("iCi")
					.input('i', Items.IRON_INGOT)
					.input('C', Items.CLAY)
					.input('R', Items.REDSTONE_BLOCK)
					.criterion(FabricRecipeProvider.hasItem(Items.CLAY), FabricRecipeProvider.conditionsFromItem(Items.CLAY))
					.criterion(FabricRecipeProvider.hasItem(Items.REDSTONE_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.REDSTONE_BLOCK))
					.criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
			);
			modRecipeProvider.addShapelessRecipe(ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.FAKE_PLAYER_SPAWN_EGG, 1)
					.input(ItemInit.PLAYER_AI)
					.input(ItemInit.PLAYER_SHELL)
					.criterion(FabricRecipeProvider.hasItem(ItemInit.PLAYER_AI), FabricRecipeProvider.conditionsFromItem(ItemInit.PLAYER_AI))
					.criterion(FabricRecipeProvider.hasItem(ItemInit.PLAYER_SHELL), FabricRecipeProvider.conditionsFromItem(ItemInit.PLAYER_SHELL))
			);
			modRecipeProvider.addShapelessRecipeWithCustomname(ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG, 1)
					.input(ItemInit.FAKE_PLAYER_SPAWN_EGG)
					.criterion(FabricRecipeProvider.hasItem(ItemInit.FAKE_PLAYER_SPAWN_EGG), FabricRecipeProvider.conditionsFromItem(ItemInit.FAKE_PLAYER_SPAWN_EGG))
			,
					new Identifier("fakeplayers:thick_to_slim"));
			modRecipeProvider.addShapelessRecipeWithCustomname(ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.FAKE_PLAYER_SPAWN_EGG, 1)
					.input(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG)
					.criterion(FabricRecipeProvider.hasItem(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG), FabricRecipeProvider.conditionsFromItem(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG))
			,
					new Identifier("fakeplayers:slim_to_thick"));

			return modRecipeProvider;
		})); // Recipes
	}

	public void generateLanguages(FabricDataGenerator.Pack pack) {
		generate_EN_US_Language(pack); // en_us (English US)
		generate_EN_UK_Language(pack); // en_uk (English UK)
		generate_FR_CA_Language(pack); // fr_ca (French Canadian)
		generate_FR_FR_Language(pack); // fr_fr (French France)
		generate_ES_AR_Language(pack); // es_ar (Spanish Argentina)
		generate_ES_CL_Language(pack); // es_cl (Spanish Chile)
		generate_ES_EC_Language(pack); // es_ec (Spanish Ecuador)
		generate_ES_ES_Language(pack); // es_es (Spanish Spain)
		generate_ES_MX_Language(pack); // es_mx (Spanish Mexico)
		generate_ES_UY_Language(pack); // es_uy (Spanish Uruguay)
		generate_ES_VE_Language(pack); // es_ve (Spanish Venezuela)
		generate_EN_AU_Language(pack); // en_au (English Australia)
		generate_EN_CA_Language(pack); // en_ca (English Canada)
		generate_EN_GB_Language(pack); // en_gb (English Great Britain)
		generate_EN_NZ_Language(pack); // en_nz (English New Zealand)
	}

	/**
	 * Adds English translations to the language file.
	 * @param output The data generator output.
	 * @param registriesFuture The registries future.
	 * @param languageType The language type.
	 * @return The ModLanguageProvider.
	 */
	public ModLanguageProvider addEnglishTranslations(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, LanguageType languageType) {
		ModLanguageProvider modLanguageProvider = new ModLanguageProvider(output, languageType);
		modLanguageProvider.addTranslation(ItemInit.PLAYER_AI, "Robot AI");
		modLanguageProvider.addTranslation(ItemInit.PLAYER_SHELL, "Robot Shell");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SPAWN_EGG, "Player Egg");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG, "Slim Player Egg");
		modLanguageProvider.addTranslation(Fakeplayers.FP_ITEM_GROUP, "Fake Players");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininput").toTranslationKey(), "NOT CURRENTLY FUNCTIONING IGNORE THIS BOX");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininputmessage").toTranslationKey(), "INPUT");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinput").toTranslationKey(), "message");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinputmessage").toTranslationKey(), "CHAT");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.send").toTranslationKey(), "Send");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.confirm").toTranslationKey(), "Confirm");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey(), "This feature is not yet implemented");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey(), "Sit");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stand").toTranslationKey(), "Stand");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stayput").toTranslationKey(), "Stay Put");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.wander").toTranslationKey(), "Wander");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.follow").toTranslationKey(), "Follow");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.name").toTranslationKey(), "Fake Player Screen");

		return modLanguageProvider;
	}

	/**
	 * Adds French translations to the language file.
	 * @param output The data generator output.
	 * @param registriesFuture The registries future.
	 * @param languageType The language type.
	 * @return The ModLanguageProvider.
	 */
	public ModLanguageProvider addFrenchTranslations(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, LanguageType languageType) {
		ModLanguageProvider modLanguageProvider = new ModLanguageProvider(output, languageType);
		modLanguageProvider.addTranslation(ItemInit.PLAYER_AI, "Robot AI");
		modLanguageProvider.addTranslation(ItemInit.PLAYER_SHELL, "Coquille de robot");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SPAWN_EGG, "Oeuf de joueur");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG, "Oeuf de joueur mince");
		modLanguageProvider.addTranslation(Fakeplayers.FP_ITEM_GROUP, "Faux joueurs");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininput").toTranslationKey(), "NE FONCTIONNE PAS ACTUELLEMENT IGNORER CETTE CASE");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininputmessage").toTranslationKey(), "ENTRÉE");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinput").toTranslationKey(), "message");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinputmessage").toTranslationKey(), "CHAT");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.send").toTranslationKey(), "Envoyer");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.confirm").toTranslationKey(), "Confirmer");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey(), "Cette fonctionnalité n'est pas encore mise en œuvre");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey(), "S'asseoir");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stand").toTranslationKey(), "Se tenir debout");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stayput").toTranslationKey(), "Ne bougez pas");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.wander").toTranslationKey(), "Errer");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.follow").toTranslationKey(), "Suivre");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.name").toTranslationKey(), "Écran de faux joueur");


		return modLanguageProvider;
	}

	/**
	 * Adds Spanish translations to the language file.
	 * @param output The data generator output.
	 * @param registriesFuture The registries future.
	 * @param languageType The language type.
	 * @return The ModLanguageProvider.
	 */
	public ModLanguageProvider addSpanishTranslations(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, LanguageType languageType) {
		ModLanguageProvider modLanguageProvider = new ModLanguageProvider(output, languageType);
		modLanguageProvider.addTranslation(ItemInit.PLAYER_AI, "IA robótica");
		modLanguageProvider.addTranslation(ItemInit.PLAYER_SHELL, "Carcasa de robot");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SPAWN_EGG, "huevo de jugador");
		modLanguageProvider.addTranslation(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG, "Huevo de jugador delgado");
		modLanguageProvider.addTranslation(Fakeplayers.FP_ITEM_GROUP, "Jugadores falsos");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininput").toTranslationKey(), "NO FUNCIONA ACTUALMENTE IGNORAR ESTA CAJA");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.skininputmessage").toTranslationKey(), "ENTRADA");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinput").toTranslationKey(), "mensaje");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.chatinputmessage").toTranslationKey(), "CHAT");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.send").toTranslationKey(), "Enviar");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.confirm").toTranslationKey(), "Confirmar");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "not_implemented_message").toTranslationKey(), "Esta característica aún no está implementada");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.sit").toTranslationKey(), "Sentarse");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stand").toTranslationKey(), "Estar de pie");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.stayput").toTranslationKey(), "No te muevas");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.wander").toTranslationKey(), "Vagar");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.follow").toTranslationKey(), "Seguir");
		modLanguageProvider.addTranslation(new Identifier(Fakeplayers.MOD_ID, "screen.fakeplayerscreen.name").toTranslationKey(), "Pantalla de jugador falso");

		return modLanguageProvider;
	}

	public void generate_EN_US_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_US))); // en_us (English US)
	}

	public void generate_EN_UK_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_UK))); // en_uk (English UK)
	}

	public void generate_FR_CA_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addFrenchTranslations(output, registriesFuture, LanguageType.FR_CA)))); // fr_ca (French Canadian)
	}

	public void generate_FR_FR_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addFrenchTranslations(output, registriesFuture, LanguageType.FR_FR)))); // fr_fr (French France)
	}

	public void generate_ES_AR_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_AR)))); // es_ar (Spanish Argentina)
	}

	public void generate_ES_CL_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_CL)))); // es_cl (Spanish Chile)
	}

	public void generate_ES_EC_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_EC)))); // es_ec (Spanish Ecuador)
	}

	public void generate_ES_ES_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_ES)))); // es_es (Spanish Spain)
	}

	public void generate_ES_MX_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_MX)))); // es_mx (Spanish Mexico)
	}

	public void generate_ES_UY_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_UY)))); // es_uy (Spanish Uruguay)
	}

	public void generate_ES_VE_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider((((output, registriesFuture) -> addSpanishTranslations(output, registriesFuture, LanguageType.ES_VE)))); // es_ve (Spanish Venezuela)
	}

	public void generate_EN_AU_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_AU))); // en_au (English Australia)
	}

	public void generate_EN_CA_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_CA))); // en_ca (English Canada)
	}

	public void generate_EN_GB_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_GB))); // en_gb (English Great Britain)
	}

	public void generate_EN_NZ_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_NZ))); // en_nz (English New Zealand)
	}




}
