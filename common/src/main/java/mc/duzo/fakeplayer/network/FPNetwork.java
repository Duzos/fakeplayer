package mc.duzo.fakeplayer.network;

import dev.architectury.networking.NetworkChannel;
import mc.duzo.fakeplayer.FakePlayerMod;
import mc.duzo.fakeplayer.entity.FakePlayerEntity;
import mc.duzo.fakeplayer.network.s2c.SendSkinS2CPacket;
import net.minecraft.util.Identifier;

public class FPNetwork {
	public static final NetworkChannel CHANNEL = NetworkChannel.create(new Identifier(FakePlayerMod.MOD_ID, "main"));

	public static void init() {
		CHANNEL.register(SendSkinS2CPacket.class, SendSkinS2CPacket::write, SendSkinS2CPacket::new, SendSkinS2CPacket::handle);
	}
}
