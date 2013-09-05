package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.Position;
import net.rs.comet.model.game.entity.player.Player;

public class SendCoordinatePacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The positioning factor of the coordinate plane.
	 */
	private Position position;
	
	/**
	 * The default class constructor for {@link SendCoordinatePacketEncoder}.
	 * 
	 * @param position The new position.
	 */
	public SendCoordinatePacketEncoder(Position position) {
		this.position = position;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 85);
		int y = position.getY() - 8 * player.getLastKnownPosition().getRegionY();
		int x = position.getX() - 8 * player.getLastKnownPosition().getRegionX();
		builder.writeNegatedByte((byte) y);
		builder.writeNegatedByte((byte) x);
		player.getChannel().write(builder);
	}
}