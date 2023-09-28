package com.duzo.fakeplayers.datagen.datagen_providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public List<ShapelessRecipeJsonBuilder> shapelessRecipes = new ArrayList<>();
    public List<ShapedRecipeJsonBuilder> shapedRecipes = new ArrayList<>();
    public HashMap<ShapelessRecipeJsonBuilder, Identifier> shapelessRecipesWithNameHashMap = new HashMap<>();
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        for (ShapelessRecipeJsonBuilder shapelessRecipeJsonBuilder : shapelessRecipes) {
            shapelessRecipeJsonBuilder.offerTo(exporter);
        }
        for (ShapedRecipeJsonBuilder shapedRecipeJsonBuilder : shapedRecipes) {
            shapedRecipeJsonBuilder.offerTo(exporter);
        }
        shapelessRecipesWithNameHashMap.forEach((shapelessRecipeJsonBuilder, identifier) -> {
            shapelessRecipeJsonBuilder.offerTo(exporter, identifier);
        });
    }

    public void addShapelessRecipe(ShapelessRecipeJsonBuilder builder) {
        if (!shapelessRecipes.contains(builder)) {
            shapelessRecipes.add(builder);
        }
    }

    public void addShapelessRecipeWithCustomname(ShapelessRecipeJsonBuilder builder, Identifier id) {
        shapelessRecipesWithNameHashMap.put(builder, id);
    }

    public void addShapedRecipe(ShapedRecipeJsonBuilder builder) {
        if (!shapedRecipes.contains(builder)) {
            shapedRecipes.add(builder);
        }
    }
}
