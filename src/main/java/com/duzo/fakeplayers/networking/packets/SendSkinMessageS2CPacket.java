package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.io.File;
import java.util.UUID;
import java.util.function.Supplier;

public class SendSkinMessageS2CPacket {
    private String name;
    public boolean messageIsValid;

    public SendSkinMessageS2CPacket(String name) {
        this.name = name;
        this.messageIsValid = true;
        System.out.println("Skin packet initialised with string : " + this.name);
    }
    public SendSkinMessageS2CPacket() {
        this.messageIsValid = false;
    }
    public static SendSkinMessageS2CPacket decode(FriendlyByteBuf buf) {
        SendSkinMessageS2CPacket skinPacket = new SendSkinMessageS2CPacket();
        try {
            String name = buf.readUtf();
            skinPacket.name = name;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading TargetEffectMessageToClient: " + e);
            return skinPacket;
        }
        skinPacket.messageIsValid = true;
        return skinPacket;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;
        buf.writeUtf(this.name);
    }

    public static void downloadAndAddSkin(String name) {
        System.out.println(name);
        SkinGrabber.downloadSkinFromUsername(name.toLowerCase().replace(" ", ""), new File(SkinGrabber.DEFAULT_DIR));
        SkinGrabber.addCustomNameToList(name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        System.out.println("Skin Packet Received");
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> downloadAndAddSkin(this.name));
        });
        return true;
    }
}
