package com.duzo.fakeplayers.datagen.datagen_providers;

import com.duzo.fakeplayers.datagen.datagen_providers.special.ModCustomSoundBuilder;
import com.duzo.fakeplayers.datagen.datagen_providers.special.ModCustomSoundProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.sound.SoundEvent;

import java.util.HashMap;

public class ModSoundProvider extends ModCustomSoundProvider {
    private final FabricDataOutput dataGenerator;

    private HashMap<String, SoundEvent[]> soundEventList = new HashMap<>();
    public ModSoundProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
        dataGenerator = dataOutput;
    }

    @Override
    public void generateSoundsData(ModCustomSoundBuilder modCustomSoundBuilder) {
        soundEventList.forEach(modCustomSoundBuilder::add);
    }

    public void addSound(String soundName, SoundEvent sound) {
        soundEventList.put(soundName, new SoundEvent[]{sound});
    }
    public void addSound(String soundName, SoundEvent[] soundEvents) {
        soundEventList.put(soundName, soundEvents);
    }
}
