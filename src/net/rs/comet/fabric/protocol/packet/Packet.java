package net.rs.comet.fabric.protocol.packet;

import net.rs.comet.utilities.ApplicationUtilities;

import org.jboss.netty.buffer.ChannelBuffer;

public class Packet {

	/**
	 * The opcode of the packet.
	 */
	private short opcode;

	/**
	 * The length of the packet.
	 */
	private int length;

	/**
	 * The dynamic buffer of the packet.
	 */
	private ChannelBuffer buffer;

	/**
	 * Represents a #317 packet.
	 * 
	 * @param buffer The dynamic buffer of the packet.
	 * 
	 * @param opcode The opcode of the packet.
	 * 
	 * @param length The length of the packet.
	 */
	public Packet(ChannelBuffer buffer, short opcode, int length) {
		setBuffer(buffer);
		setOpcode(opcode);
		setLength(length);
	}

	/**
	 * Retrieves the dynamic buffer of the packet.
	 * 
	 * @return The buffer that was retrieved.
	 */
	public ChannelBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Retrieves the length of the packet.
	 * 
	 * @return The retrieved length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Retrieves the opcode of the packet.
	 * 
	 * @return The retrieved opcode.
	 */
	public short getOpcode() {
		return opcode;
	}

	/**
	 * Modifies the dynamic buffer of the packet.
	 * 
	 * @param buffer The new modification.
	 */
	public void setBuffer(ChannelBuffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * Modifies the length of the packet.
	 * 
	 * @param length The new modification.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Modifies the opcode of the packet.
	 * 
	 * @param opcode The new modification.
	 */
	public void setOpcode(short opcode) {
		this.opcode = opcode;
	}

	/**
	 * Reads a RuneScape style string.
	 * 
	 * @return The string that was read.
	 */
	public String getRuneScapeString() {
		return ApplicationUtilities.readRuneScapeString(buffer);
	}

	/**
	 * Reads the unsigned subtrahend from the buffer.
	 * 
	 * @return The unsigned byte value.
	 */
	public int readSubtrahend() {
		return 128 - buffer.readUnsignedByte();
	}

	/**
	 * Reads given amount of bytes to the array along with addition to the values.
	 * 
	 * @param amount The amount of bytes.
	 * 
	 * @return The bytes array.
	 */
	public byte[] readByteChainAdditional(int amount) {
		byte[] bytes = new byte[amount];
		for (int i = 0; i < amount; i++) {
			bytes[i] = (byte) (buffer.readByte() + 128);
		}
		return bytes;
	}
	
	/**
	 * Reads a negated byte from the buffer.
	 * 
	 * @return The byte that was read.
	 */
	public byte readNegatedByte() {
		return (byte) (-buffer.readByte());
	}
	
	/**
	 * Reads a little short with an addition from the buffer.
	 * 
	 * @return The short that was read.
	 */
	public short readLittleEndianShortAddition(){
		int i = (buffer.readByte() - 128 & 0xff) | ((buffer.readUnsignedByte()) << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return (short) i;
	}
	
	/**
	 * Reads a little short from the buffer.
	 * 
	 * @return The short that was read.
	 */
	public short readLittleEndianShort() {
		int i = (buffer.readUnsignedByte()) | ((buffer.readUnsignedByte()) << 8);
		if (i > 32767) {
			i -= 0x10000;
		}
		return (short) i;
	}
	
	/**
	 * Reads a short with an addition from the buffer.
	 * 
	 * @return The short that was read.
	 */
	public short readAdditionalShort() {
        int i = ((buffer.readByte() & 0xFF) << 8) | (buffer.readByte() - 128 & 0xFF);
        if(i > 32767) {
            i -= 0x10000;
        }
        return (short) i;
    }

	/**
	 * Reads a single signed byte.
	 * 
	 * @return The byte that was read.
	 */
	public int readByte() {
		return buffer.readByte();
	}
}