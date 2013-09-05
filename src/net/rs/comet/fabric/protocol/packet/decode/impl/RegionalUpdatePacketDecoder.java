package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.object.GameObjectHandler;

public class RegionalUpdatePacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		/*
		 * Loads the new regional objects.
		 */
		GameObjectHandler.getSingleton().sendLocalObjects();
	}
}
