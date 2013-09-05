package net.rs.comet.fabric.protocol.codec;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.PacketRegistrey;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.utilities.ApplicationConstants;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public class PacketProtocolDecoder extends ReplayingDecoder<PacketProtocolDecoderState> {

	/**
	 * The player association.
	 */
	private Player player;

	/**
	 * The opcode of the packet.
	 */
	private short opcode = -1;

	/**
	 * The length of the packet.
	 */
	private int length =-1;

	/**
	 * Retrieves the length of an incoming packet.
	 * 
	 * @return The length that was retrieved.
	 */
	public int getLength() {
		return length;
	} 

	/**
	 * Retrieves the opcode of an incoming packet.
	 * 
	 * @return The retrieved opcode.
	 */
	public short getOpcode() {
		return opcode;
	}

	/**
	 * Retrieves the player association.
	 * 
	 * @return The retrieved player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Modifies the length of an incoming packet.
	 * 
	 * @param length The new modification.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Modifies the opcode of an incoming packet.
	 * 
	 * @param opcode The new modification.
	 */
	public void setOpcode(short opcode) {
		this.opcode = opcode;
	}

	/**
	 * Modifies the player association.
	 * 
	 * @param player The new modification.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Handles the operation of decoding incoming packets.
	 * 
	 * @param player The player association.
	 */
	public PacketProtocolDecoder(Player player) {
		checkpoint(PacketProtocolDecoderState.DECODE_OPCODE);
		setPlayer(player);
	}

	@Override
	protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer, PacketProtocolDecoderState state) throws Exception {
		switch (state) {

		case DECODE_OPCODE:
			if (getOpcode() == -1) {
				/*
				 * Reads the packet opcode as an unsigned byte.
				 */
				setOpcode(buffer.readUnsignedByte());
				/*
				 * Decodes the packet opcode using the ISAAC algorithm.
				 */
				setOpcode((short) (getOpcode() - player.getDecoder().getNextValue() & 0xFF));
			}
			checkpoint(PacketProtocolDecoderState.DECODE_LENGTH);
			return true;

		case DECODE_LENGTH:
			/*
			 * Finds the packet length based on the opcode.
			 */
			setLength(ApplicationConstants.PACKET_SIZES[getOpcode()]);
			/*
			 * If the length isn't positive it needs to be read.
			 */
			if (getLength() == -1) {
				setLength(buffer.readUnsignedByte());
			}
			checkpoint(PacketProtocolDecoderState.DISPENSE_PACKET);
			return true;

		case DISPENSE_PACKET:
			/*
			 * Constructs a new array of bytes to be read based on the length.
			 */
			final byte[] data = new byte[length];
			/*
			 * Reads the chain of bytes pertaining to the length.
			 */
			buffer.readBytes(data);
			/*
			 * Allocates a new dynamic buffer used for writing the data back.
			 */
			final ChannelBuffer payload = ChannelBuffers.buffer(length);
			/*
			 * Writes the data back completing the packet decoding procedure.
			 */
			payload.writeBytes(data);
			/*
			 * Dispenses the packet to a handler.
			 */
			Packet packet = new Packet(payload, getOpcode(), getLength());
			PacketRegistrey.readPacket(player, packet);
			/*
			 * Resets the read values to allow for another cycle.
			 */
			setOpcode((short) -1);
			setLength(-1);
			/*
			 * Sets the current state back to starting position.
			 */
			checkpoint(PacketProtocolDecoderState.DECODE_OPCODE);
			return true;
		}
		return false;
	} 
}