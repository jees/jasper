package org.jasper.packet;

/**
 * The enumeration of the packet types
 * @author Ares_
 *
 */
public enum PacketType {

	/**
     * A fixed size packet where the size never changes.
     */
    FIXED,

    /**
     * A variable packet where the size is described by a byte.
     */
    VARIABLE_BYTE,

    /**
     * A variable packet where the size is described by a word.
     */
    VARIABLE_SHORT;
    
}