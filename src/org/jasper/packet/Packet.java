package org.jasper.packet;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * The structure of a packet
 * @author Ares_
 *
 */
public final class Packet {

	/**
	 * The operation code of the packet
	 */
	private final short opcode;
	
	/**
	 * The payload of the packet
	 */
	private final ChannelBuffer payload;
	
	/**
	 * The {@link PacketType}
	 */
	private PacketType type;
	
	/**
	 * Constructs a new {@link Packet}
	 * @param opcode The opcode of packet
	 * @param type The type of packet
	 * @param payload The payload of packet
	 */
	public Packet(int opcode, PacketType type, ChannelBuffer payload) {
		this.opcode = (short)opcode;
		this.type = type;
		this.payload = payload;
	}
	
	/**
	 * Gets the packet type
	 * @return The packet type
	 */
	public PacketType getType() {
		return type;
	}
	
	/**
	 * Gets the payload of the packet
	 * @return The payload of packet
	 */
	public ChannelBuffer getPayload() {
		return payload;
	}
	
	/**
	 * Gets the opcode of the packet
	 * @return The opcode of packet
	 */
	public short getOpcode() {
		return opcode;
	}
	
}