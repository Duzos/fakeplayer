package mc.duzo.fakeplayer.network.s2c;

import java.io.File;
import java.util.function.Supplier;

import dev.architectury.networking.NetworkManager;

import net.minecraft.network.PacketByteBuf;

import mc.duzo.fakeplayer.entity.FakePlayerEntity;
import mc.duzo.fakeplayer.util.SkinGrabber;

public record SendSkinS2CPacket(String uuid, String path, String name, String url)  {
    public SendSkinS2CPacket(FakePlayerEntity entity) {
        this(entity.getUuidAsString(), SkinGrabber.DEFAULT_DIR, entity.getUuidAsString(), entity.getURL());
    }
    public SendSkinS2CPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readString(), buf.readString(), buf.readString());
    }

    public void write(PacketByteBuf buf) {
        buf.writeString(uuid);
        buf.writeString(path);
        buf.writeString(name);
        buf.writeString(url);
    }
    public void handle(Supplier<NetworkManager.PacketContext> context) {
        SkinGrabber.downloadImageFromURL(name.toLowerCase().replace(" ", ""), new File(path), url);
        SkinGrabber.addCustomNameToList(name);
    }
}
