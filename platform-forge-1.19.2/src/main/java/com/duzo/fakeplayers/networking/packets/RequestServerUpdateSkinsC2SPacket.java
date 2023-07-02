//package com.duzo.fakeplayers.networking.packets;
//
//import com.duzo.fakeplayers.client.threads.ImageDownloaderThread;
//import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
//import com.duzo.fakeplayers.networking.Network;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.fml.DistExecutor;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.io.File;
//import java.util.function.Supplier;
//
//public class RequestServerUpdateSkinsC2SPacket {
//    private int id;
//    public boolean messageIsValid;
//
//    public RequestServerUpdateSkinsC2SPacket(int id) {
//        this.id = id;
//        this.messageIsValid = true;
//    }
//    public RequestServerUpdateSkinsC2SPacket() {
//        this.messageIsValid = false;
//    }
//    public static RequestServerUpdateSkinsC2SPacket decode(FriendlyByteBuf buf) {
//        RequestServerUpdateSkinsC2SPacket packet = new RequestServerUpdateSkinsC2SPacket();
//        try {
//            packet.id = buf.readInt();
//        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
//            System.out.println("Exception while reading Packet: " + e);
//            return packet;
//        }
//        packet.messageIsValid = true;
//        return packet;
//    }
//
//    public void encode(FriendlyByteBuf buf) {
//        if (!this.messageIsValid) return;
//        buf.writeInt(this.id);
//    }
//
//    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
//        NetworkEvent.Context context = supplier.get();
//        context.enqueueWork(() -> {
//            // oh yo waassup client you want me ot inform yo fellow clients ight bet
//            // (this totally wont be exploited by hackers somehow)
//            // I mean whats wrong with a client being able to download things onto someone elses computer
//            // @TODO dont do that :)
//
//            Level level = context.getSender().getLevel();
//            System.out.println(level.isClientSide);
//
//            if (level.getEntity(this.id) instanceof FakePlayerEntity player) {
//                player.updateAllClientSkins();
//            }
//        });
//        return true;
//    }
//}


package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.client.threads.ImageDownloaderThread;
import com.duzo.fakeplayers.client.threads.SkinDownloaderThread;
import com.duzo.fakeplayers.networking.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.io.File;
import java.util.function.Supplier;

public class RequestServerUpdateSkinsC2SPacket {
    private String filename;
    private File filepath;
    private String URL;
    private String uuid;
    public boolean messageIsValid;

    public RequestServerUpdateSkinsC2SPacket(String uuid,String filename, File filepath, String URL) {
        this.uuid = uuid;
        this.filename = filename;
        this.filepath = filepath;
        this.URL = URL;
        this.messageIsValid = true;
    }
    public RequestServerUpdateSkinsC2SPacket() {
        this.messageIsValid = false;
    }
    public static RequestServerUpdateSkinsC2SPacket decode(FriendlyByteBuf buf) {
        RequestServerUpdateSkinsC2SPacket packet = new RequestServerUpdateSkinsC2SPacket();
        try {
            String uuid = buf.readUtf();
            String filename = buf.readUtf();
            String filepathString = buf.readUtf();
            String URL = buf.readUtf();
            File filepath = new File(filepathString);

            packet.uuid = uuid;
            packet.filename = filename;
            packet.filepath = filepath;
            packet.URL = URL;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }
        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;
        buf.writeUtf(this.uuid);
        buf.writeUtf(this.filename);
        buf.writeUtf(this.filepath.getAbsolutePath());
        buf.writeUtf(this.URL);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Network.sendToAll(new SendImageDownloadMessageS2CPacket(this.uuid,this.filename,this.filepath,this.URL));
        });
        return true;
    }
}
