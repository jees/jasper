package net.rs.comet.fabric.protocol.codec;

public enum PacketProtocolDecoderState {
	
	/*
	 * Decodes the numerical index of the packet.
	 */
	DECODE_OPCODE,
	
	/*
	 * Decodes the numerical length of the packet.
	 */
	DECODE_LENGTH,
	
	/*
	 * Dispenses the packet to a handler after the procedure
	 * has been completed.
	 */
	DISPENSE_PACKET,
}
