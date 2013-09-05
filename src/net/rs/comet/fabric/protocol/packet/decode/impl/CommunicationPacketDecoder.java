package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.model.game.entity.player.Player;

public class CommunicationPacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		int effects = packet.readSubtrahend();
		int color = packet.readSubtrahend();
		int length = (packet.getLength() - 2);
		byte[] text = packet.readByteChainAdditional(length);
		player.setChatTextEffects(effects);
		player.setChatTextColor(color);
		player.setChatText(text);
		player.setChatUpdateRequired(true);
	}
}