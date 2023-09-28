package com.duzo.fakeplayers.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public class FakePlayerSittingComponent implements AutoSyncedComponent, Component {

    private final Entity provider;
    private boolean sitting = false;
    public FakePlayerSittingComponent(Entity entity) {
        this.provider = entity;
    }
    @Override
    public void readFromNbt(NbtCompound tag) {
        tag.putBoolean("sitting", this.sitting);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        this.sitting = tag.getBoolean("sitting");
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
        MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.sync(this.provider);
    }

    public void toggleSitting() {
        this.setSitting(!this.sitting);
    }
}
