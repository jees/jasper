package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.object.GameObject;

public class SendGameObjectPacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The object being created.
	 */
	private GameObject object;
	
	/**
	 * The default class constructor for {@link SendGameObjectPacketEncoder}.
	 * 
	 * @param object The object being created.
	 */
	public SendGameObjectPacketEncoder(GameObject object) {
		this.object = object;
	}
	
	@Override
	public void encodePacket(Player player) {
		player.writePacketMessage(new SendCoordinatePacketEncoder(object.getPosition()));
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 151);
		builder.writeSubtrahendByte((byte) 0);
		builder.writeLittleEndianShort(object.getIndex());
		builder.writeSubtrahendByte((byte) ((object.getIndexType() << 2) + (object.getRotation() & 3)));
		player.getChannel().write(builder);
	}
}