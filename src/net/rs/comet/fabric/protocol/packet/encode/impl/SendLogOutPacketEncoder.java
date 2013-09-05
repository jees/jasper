package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.player.Player;

public class SendLogOutPacketEncoder implements PacketProtocolMessageEncoder {

	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 109);
		player.getChannel().write(builder);
		Game.getSingleton().removePlayer(player);
		System.out.println("A connection has been removed for: [" + player.getChannel() + "].");
	}
}