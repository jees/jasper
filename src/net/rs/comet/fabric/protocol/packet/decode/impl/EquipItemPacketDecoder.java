package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.model.game.entity.player.Player;

public class EquipItemPacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		@SuppressWarnings("unused")
		int identity = packet.getBuffer().readShort();
		@SuppressWarnings("unused")
		int slot = packet.readAdditionalShort();
		@SuppressWarnings("unused")
		int concreate = packet.getBuffer().readShort();
	}
}