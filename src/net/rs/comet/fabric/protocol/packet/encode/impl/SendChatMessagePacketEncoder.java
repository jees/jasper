package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendChatMessagePacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The message to be sent to the players chat-box.
	 */
	private String message;
	
	/**
	 * The default class constructor for {@link SendChatMessagePacketEncoder}.
	 * 
	 * @param message The message to be sent.
	 */
	public SendChatMessagePacketEncoder(String message) {
		this.message = message;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeVariablePacketHeader(player.getEncoder(), 253);
		builder.writeString(message);
		builder.finishVariablePacketHeader();
		player.getChannel().write(builder);
	}
}