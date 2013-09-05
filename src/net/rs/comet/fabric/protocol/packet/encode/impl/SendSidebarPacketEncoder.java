package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendSidebarPacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The placing format of the interface.
	 */
	private int interfaceForm;

	/**
	 * The identity number of the side-bar.
	 */
	private int identity;

	/**
	 * Retrieves the identity number of the interface.
	 * 
	 * @return The identity that was retrieved.
	 */
	public int getIdentity() {
		return identity;
	}

	/**
	 * Modifies the identity number of the interface.
	 * 
	 * @param identity The new modification.
	 */
	public void setIdentity(int identity) {
		this.identity = identity;
	}

	/**
	 * Retrieves the formatting of the side-bar.
	 * 
	 * @return The retrieved format.
	 */
	public int getInterfaceForm() {
		return interfaceForm;
	}

	/**
	 * Modifies the formatting of the side-bar.
	 * 
	 * @param format The new modification
	 */
	public void setInterfaceForm(int format) {
		this.interfaceForm = format;
	}

	/**
	 * The default class constructor for {@link SendSidebarPacketEncoder}.
	 * 
	 * @param form The formatting of the interface.
	 * 
	 * @param identity The identity number of the interface.
	 */
	public SendSidebarPacketEncoder(int format, int identity) {
		setInterfaceForm(format);
		setIdentity(identity);
	}

	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 71);
		builder.writeShort((short) getIdentity());
		builder.writeByteAddition((byte) getInterfaceForm());
		player.getChannel().write(builder);
	}
}