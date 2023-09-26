package com.duzo.fakeplayers.components;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.util.SkinGrabber;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import java.io.File;
import java.util.UUID;

import static com.duzo.fakeplayers.util.SkinGrabber.URL;

public class FakePlayerSkinComponent implements AutoSyncedComponent, Component {

    private final Entity provider;

    public FakePlayerSkinComponent(Entity provider) {
        this.provider = provider;
    }

    private String SKIN_URl = "";
    @Override
    public void readFromNbt(NbtCompound tag) {
        this.setURL(tag.getString("skin_url"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("skin_url", this.getURL());
    }

    public void setURL(String string) {
        this.SKIN_URl = string;
        MyComponents.FAKE_PLAYER_SKIN_COMPONENT_COMPONENT.sync(this.provider);
        if (!Fakeplayers.urlsToDownload.contains(this.getURL())) {
            Fakeplayers.urlsToDownload.add(this.getURL());
        }
    }

    public String getURL() {
        return this.SKIN_URl;
    }


    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        AutoSyncedComponent.super.applySyncPacket(buf);
        if (this.SKIN_URl != null) {
            String username = this.getURL().replace(URL, "");
            SkinGrabber.downloadImageFromURL(this.getURL());
            SkinGrabber.addEntitySkinToList(this.provider, username);
        }

    }
}
