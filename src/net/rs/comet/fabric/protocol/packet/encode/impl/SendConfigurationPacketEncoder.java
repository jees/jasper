package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendConfigurationPacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The value of the configuration.
	 */
	private int value;
	
	/**
	 * The index of the configuration.
	 */
	private int index;
	
	/**
	 * The default class constructor for {@link SendConfigurationPacketEncoder}.
	 * 
	 * @param value The value.
	 * 
	 * @param identity The identity.
	 */
	public SendConfigurationPacketEncoder(int index, int value) {
		this.value = value;
		this.index = index;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 36);
		builder.writeLittleEndianShort(index);
		builder.writeByte((byte) value);
		player.getChannel().write(builder);
	}
}