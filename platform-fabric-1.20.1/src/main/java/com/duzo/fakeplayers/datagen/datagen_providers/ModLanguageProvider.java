package com.duzo.fakeplayers.datagen.datagen_providers;

import com.duzo.fakeplayers.datagen.LanguageType;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ModLanguageProvider extends FabricLanguageProvider {

    public HashMap <String, String> translations = new HashMap<>();

    public LanguageType languageType;
    private final FabricDataOutput dataGenerator;

    public ModLanguageProvider(FabricDataOutput dataOutput, LanguageType languageType) {
        super(dataOutput, languageType.name().toLowerCase());
        this.languageType = languageType;
        this.dataGenerator = dataOutput;
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (String key : translations.keySet()) {
            translationBuilder.add(key, translations.get(key));
        }

        try {
            translationBuilder.add(dataGenerator.getModContainer().findPath("assets/fakeplayers/lang/" + languageType.name().toLowerCase() + ".existing.json").get());
        } catch (Exception e) {
            LOGGER.warn("Failed to add existing language file! " + "(" + languageType.name().toLowerCase() + ") | ", e);
//            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }

    /**
     * Adds a translation to the language file.
     * @param item The item to add the translation for.
     * @param translation The translation.
     */
    public void addTranslation(Item item, String translation) {
        translations.put(item.getTranslationKey(), translation);
    }

    /**
     * Adds a translation to the language file.
     * @param itemGroup The item group to add the translation for.
     * @param translation The translation.
     */
    public void addTranslation(OwoItemGroup itemGroup, String translation) {
        translations.put(getTranslationKeyForOwoItemGroup(itemGroup), translation);
    }

    /**
     * Adds a translation to the language file.
     * @param key The key to add the translation for.
     * @param translation The translation.
     */
    public void addTranslation(String key, String translation) {
        translations.put(key, translation);
    }

    /**
     * Adds a translation to the language file
     * @param block The block to add the translation for
     * @param translation The translation
     */
    public void addTranslation(Block block, String translation) {
        translations.put(block.getTranslationKey(), translation);
    }

    /**
     *  Get the translation key for the OwoItemGroup
     * @param itemGroup The item group to get the translation key for
     * @return The translation key for the item group
     */

    private String getTranslationKeyForOwoItemGroup(OwoItemGroup itemGroup) {
        return "itemGroup." + itemGroup.id().getNamespace() + "." + itemGroup.id().getPath();
    }

}
