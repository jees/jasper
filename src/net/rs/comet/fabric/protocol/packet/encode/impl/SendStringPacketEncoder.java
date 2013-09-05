package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendStringPacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The string that is being written.
	 */
	private String text;
	
	/**
	 * The identity key of that string.
	 */
	private int identity;
	
	/**
	 * The default class constructor for {@link SendStringPacketEncoder}.
	 * 
	 * @param identity The number identity of the string to be written.
	 * 
	 * @param text The string to be written.
	 */
	public SendStringPacketEncoder(int identity, String text) {
		this.text = text;
		this.identity = identity;
	}
	
	@Override
	public void encodePacket(Player player) {
		 PacketBuilder builder = new PacketBuilder();
         builder.writeVariableShortPacketHeader(player.getEncoder(), 126);
         builder.writeString(text);
         builder.writeAdditionalShort(identity);
         builder.finishVariableShortPacketHeader();
         player.getChannel().write(builder);
	}
}