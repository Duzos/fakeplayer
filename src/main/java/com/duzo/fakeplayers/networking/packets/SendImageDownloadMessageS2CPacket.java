package com.duzo.fakeplayers.networking.packets;

import com.duzo.fakeplayers.client.threads.ImageDownloaderThread;
import com.duzo.fakeplayers.client.threads.SkinDownloaderThread;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.io.File;
import java.util.function.Supplier;

public class SendImageDownloadMessageS2CPacket {
    private String filename;
    private File filepath;
    private String URL;
    private String username;
    public boolean messageIsValid;

    public SendImageDownloadMessageS2CPacket(String username,String filename, File filepath, String URL) {
        this.username = username;
        this.filename = filename;
        this.filepath = filepath;
        this.URL = URL;
        this.messageIsValid = true;
    }
    public SendImageDownloadMessageS2CPacket() {
        this.messageIsValid = false;
    }
    public static SendImageDownloadMessageS2CPacket decode(FriendlyByteBuf buf) {
        SendImageDownloadMessageS2CPacket packet = new SendImageDownloadMessageS2CPacket();
        try {
            String username = buf.readUtf();
            String filename = buf.readUtf();
            String filepathString = buf.readUtf();
            String URL = buf.readUtf();
            File filepath = new File(filepathString);

            packet.username = username;
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
        buf.writeUtf(this.username);
        buf.writeUtf(this.filename);
        buf.writeUtf(this.filepath.getAbsolutePath());
        buf.writeUtf(this.URL);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ImageDownloaderThread thread = new ImageDownloaderThread(this.username,this.filename,this.filepath,this.URL);
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> thread);
        });
        return true;
    }
}
