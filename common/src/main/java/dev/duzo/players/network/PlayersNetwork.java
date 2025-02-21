package dev.duzo.players.network;

import commonnetwork.api.Network;
import dev.duzo.players.network.c2s.SetSkinKeyPacketC2S;
import dev.duzo.players.network.s2c.OpenScreenPacketS2C;

public class PlayersNetwork {
	public static void init() {
		Network.registerPacket(OpenScreenPacketS2C.LOCATION, OpenScreenPacketS2C.class, OpenScreenPacketS2C::encode, OpenScreenPacketS2C::decode, OpenScreenPacketS2C::handle);
		Network.registerPacket(SetSkinKeyPacketC2S.LOCATION, SetSkinKeyPacketC2S.class, SetSkinKeyPacketC2S::encode, SetSkinKeyPacketC2S::decode, SetSkinKeyPacketC2S::handle);
	}
}
