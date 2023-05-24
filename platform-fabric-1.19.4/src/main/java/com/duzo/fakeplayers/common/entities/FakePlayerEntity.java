package com.duzo.fakeplayers.common.entities;

import com.duzo.fakeplayers.network.NetworkConstants;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerPlayNetworkAddon;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FakePlayerEntity extends HumanoidEntity{
    private static final TrackedData<String> SKIN_URL = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.STRING);

    public FakePlayerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setURL(nbt.getString("skinURL"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("skinURL",this.getURL());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SKIN_URL,"");
    }

    private void setURL(String url) {
        this.dataTracker.set(SKIN_URL,url);
    }
    public String getURL() {
        return this.dataTracker.get(SKIN_URL);
    }

    public void updateSkin() {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeString(this.getUuidAsString());
        buf.writeString(this.getUuidAsString());
        buf.writeString(SkinGrabber.DEFAULT_DIR);
        buf.writeString(this.getURL());

        NetworkConstants.sendPacketToAll(this.getServer(),NetworkConstants.SEND_SKIN_PACKET_ID, buf);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (!this.world.isClient()) {
            if (name.getString().equals("")) {return;}

            this.setURL(SkinGrabber.URL + name.getString());
            this.updateSkin();
        }
    }
}
