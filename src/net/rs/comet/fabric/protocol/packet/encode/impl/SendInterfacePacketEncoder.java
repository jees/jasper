package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendInterfacePacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The interface index;
	 */
	int interfaceIndex;
	
	/**
	 * The default class constructor for {@link SendInterfacePacketEncoder}.
	 * 
	 * @param interfaceIndex The interface index.
	 */
	public SendInterfacePacketEncoder(int interfaceIndex) {
		this.interfaceIndex = interfaceIndex;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 97);
		builder.writeShort((short) interfaceIndex);
		player.getChannel().write(builder);
	}
}