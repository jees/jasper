package net.rs.comet.fabric.protocol.packet;

import net.rs.comet.fabric.protocol.packet.security.ISAACCipher;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class PacketBuilder {

	/**
	 * The dynamic buffer backing the construction.
	 */
	private ChannelBuffer buffer;

	/**
	 * The current bit position.
	 */
	private int bitPosition;

	/**
	 * The position of the packet length.
	 */
	private int lengthPosition;

	/**
	 * The bit mask array.
	 */
	public static final int[] BIT_MASK_OUT = new int[32];

	/**
	 * Statically populates the bit mask array.
	 */
	static {
		for (int i = 0; i < BIT_MASK_OUT.length; i++)
			BIT_MASK_OUT[i] = (1 << i) - 1;
	}

	/**
	 * The default class constructor for {@link PacketBuilder}.
	 */
	public PacketBuilder() {
		setBuffer(ChannelBuffers.dynamicBuffer());
	}

	/**
	 * Stops the bit access.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder finishBitAccess() {
		buffer.writerIndex((bitPosition + 7) / 8);
		return this;
	}

	/**
	 * Finishes a variable short sized packet header.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder finishVariableShortPacketHeader() {
		buffer.setShort(lengthPosition, (buffer.writerIndex()- lengthPosition - 2));
		return this;
	}

	/**
	 * Retrieves the dynamic buffer.
	 * 
	 * @return The buffer that was retrieved.
	 */
	public ChannelBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Modifies the dynamic buffer.
	 * 
	 * @param buffer The new modification.
	 */
	public void setBuffer(ChannelBuffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * Starts the bit access.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder startBitAccess() {
		bitPosition = buffer.writerIndex() * 8;
		return this;
	}
	
	/**
	 * Writes a singular string broken down into bytes.
	 * 
	 * @param toWrite The string to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeString(String toWrite) {
		for (byte value : toWrite.getBytes()) {
			writeByte(value);
		}
		/*
		 * The indicator to stop the procedure.
		 */
		buffer.writeByte(10);
		return this;
	}

	/**
	 * Starts the byte access.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder startByteAccess() {
		buffer.writerIndex((bitPosition + 7) / 8);
		return this;
	}
	
	/**
     * Writes a little integer.
     * 
     * @param val The value.
     * 
     * @return The PacketBuilder instance, for chaining.
     */
	public PacketBuilder writeLittleEndianInteger(int val) {
		buffer.writeByte((byte) (val));
		buffer.writeByte((byte) (val >> 8));
		buffer.writeByte((byte) (val >> 16));
		buffer.writeByte((byte) (val >> 24));
		return this;
	}
	
	/**
     * Puts a subtrahend byte in the buffer.
     * 
     * @param val The value.
     * 
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder writeSubtrahendByte(byte val) {
    	buffer.writeByte((byte) (128 - val));
        return this;
    }
    
    /**
     * Writes a little short with an addition.
     * 
     * @param val The value.
     * 
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder writeAdditionalLittleEndianShort(int val) {
    	buffer.writeByte((byte) (val + 128));
    	buffer.writeByte((byte) (val >> 8));
        return this;
    }
    
    /**
     * Writes a type one integer.
     * 
     * @param val The value.
     * 
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder writeInteger1(int val) {
    	buffer.writeByte((byte) (val >> 8));
    	buffer.writeByte((byte) val);
    	buffer.writeByte((byte) (val >> 24));
    	buffer.writeByte((byte) (val >> 16));
        return this;
    }

	/**
	 * Writes a single signed byte.
	 * 
	 * @param write The byte to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeByte(byte write) {
		buffer.writeByte(write);
		return this;
	}

	/**
	 * Writes a single byte with an addition.
	 * 
	 * @param write The byte to be written with an addition.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeByteAddition(byte write) {
		buffer.writeByte(write + 128);
		return this;
	}

	/**
	 * Writes a signed little short.
	 * 
	 * @param value The signed short.
	 */
	public PacketBuilder writeLittleEndianShort(int value) {
		getBuffer().writeByte(value);
		getBuffer().writeByte(value >> 8);
		return this;
	}

	/**
	 * Writes a standard packet header.
	 * 
	 * @param cipher The encryption code.
	 * 
	 * @param value The packet opcode.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeRawPacketHeader(ISAACCipher cipher, int value) {
		writeByte((byte) (value + cipher.getNextValue()));
		return this;
	}

	/**
	 * Writes a single integer.
	 * 
	 * @param write The integer to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeInteger(int write) {
		buffer.writeInt(write);
		return this;
	}

	/**
	 * Writes the bytes from the argued byte array into this buffer, in reverse.
	 * 
	 * @param data The data to write.
	 */
	public void writeBytesReverse(byte[] data) {
		for (int i = data.length - 1; i >= 0; i--) {
			writeByte((byte) data[i]);
		}
	}
	
	/**
	 * Writes an array of bytes.
	 * 
	 * @param buffer The byte array.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder write(byte[] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			getBuffer().writeByte(buffer[i]);
		}
		return this;
	}

	/**
	 * Writes a single long.
	 * 
	 * @param write The long to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeLong(long write) {
		buffer.writeLong(write);
		return this;
	}

	/**
	 * Writes a single negated byte.
	 * 
	 * @param write The byte to be negated.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeNegatedByte(byte write) {
		buffer.writeByte(-write);
		return this;
	}

	/**
	 * Writes a single short.
	 * 
	 * @param write The short to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeShort(short write) {
		buffer.writeShort(write);
		return this;
	}

	/**
	 * Writes a variable short sized packet header.
	 * 
	 * @param cipher The encryption key.
	 * 
	 * @param value The opcode.
	 */
	public PacketBuilder writeVariableShortPacketHeader(ISAACCipher cipher, int value) {
		writeRawPacketHeader(cipher, value);
		lengthPosition = buffer.writerIndex();
		writeShort((short) 0);
		return this;
	}

	/**
	 * Writes a short with a standard addition.
	 * 
	 * @param value The value to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeAdditionalShort(int value) {
		buffer.writeByte((byte) (value >> 8));
		buffer.writeByte((byte) (value + 128));
		return this;
	}

	/**
	 * Checks if the dynamic buffer has data remaining.
	 * 
	 * @return The result of the operation.
	 */
	public boolean isEmpty() {
		return buffer.writerIndex() == 0;
	}

	/**
	 * Writes an array of bytes up the channel.
	 * 
	 * @param bytes The array to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeBytes(byte[] bytes) {
		buffer.writeBytes(bytes);
		return this;
	}

	/**
	 * Writes a variable sized packet header.
	 * 
	 * @param cipher The encryption key.
	 * 
	 * @param value The value.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeVariablePacketHeader(ISAACCipher cipher, int value) {
		writeRawPacketHeader(cipher, value);
		lengthPosition = buffer.writerIndex();
		buffer.writeByte(0);
		return this;
	}

	/**
	 * Kills a variable sized packet header.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder finishVariablePacketHeader() {
		buffer.setByte(lengthPosition, (buffer.writerIndex() - lengthPosition - 1));
		return this;
	}

	/**
	 * Writes a series of bits to the dynamic buffer.
	 * 
	 * @param number The number of bits.
	 * 
	 * @param value The value of the bits.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeBits(int number, final int value) {
		final int bytes = (int) Math.ceil(number / 8D) + 1;
		buffer.ensureWritableBytes((bitPosition + 7) / 8 + bytes);
		final byte[] buffers = buffer.array();
		int bytePos = bitPosition >> 3;
		int bitOffset = 8 - (bitPosition & 7);
		bitPosition += number;
		for (; number > bitOffset; bitOffset = 8) {
			buffers[bytePos] &= ~BIT_MASK_OUT[bitOffset];
			buffers[bytePos++] |= (value >> (number - bitOffset)) & BIT_MASK_OUT[bitOffset];
			number -= bitOffset;
		}
		if (number == bitOffset) {
			buffers[bytePos] &= ~BIT_MASK_OUT[bitOffset];
			buffers[bytePos] |= value & BIT_MASK_OUT[bitOffset];
		} else {
			buffers[bytePos] &= ~(BIT_MASK_OUT[number] << (bitOffset - number));
			buffers[bytePos] |= (value & BIT_MASK_OUT[number]) << (bitOffset - number);
		}
		return this;
	}

	/**
	 * Writes a series of bytes derived from the dynamic buffer.
	 * 
	 * @param buffer The bytes to be written.
	 * 
	 * @return The super type for chaining.
	 */
	public PacketBuilder writeDynamicBuffer(ChannelBuffer buffer) {
		byte[] bytes = new byte[buffer.readableBytes()];
		buffer.markReaderIndex();
		try {
			buffer.readBytes(bytes);
		} finally {
			buffer.resetReaderIndex();
		}
		writeBytes(bytes);
		return this;
	}
}