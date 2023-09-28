package com.duzo.fakeplayers.datagen.datagen_providers.special;

import net.minecraft.sound.SoundEvent;

@FunctionalInterface
public interface ModCustomSoundBuilder {
    void add (String soundName, SoundEvent[] soundEvents);
    default void add(String soundName, SoundEvent soundEvent) {
        add(soundName, new SoundEvent[]{soundEvent});
    }
}
