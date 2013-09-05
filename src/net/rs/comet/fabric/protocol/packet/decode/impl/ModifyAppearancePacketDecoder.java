package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.model.game.entity.player.Player;

public class ModifyAppearancePacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		player.setGender(packet.readByte());
		player.getAppearanceValues()[3] = packet.readByte();
		player.getAppearanceValues()[6] = packet.readByte();
		player.getAppearanceValues()[0] = packet.readByte();
		player.getAppearanceValues()[1] = packet.readByte();
		player.getAppearanceValues()[4] = packet.readByte();
		player.getAppearanceValues()[2] = packet.readByte();
		player.getAppearanceValues()[5] = packet.readByte();
		player.getColorValues()[0] = packet.readByte();
		player.getColorValues()[1] = packet.readByte();
		player.getColorValues()[2] = packet.readByte();
		player.getColorValues()[3] = packet.readByte();
		player.getColorValues()[4] = packet.readByte();
	}
}