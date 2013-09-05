package net.rs.comet.utilities;

import net.rs.comet.model.game.Position;

import org.jboss.netty.buffer.ChannelBuffer;

public class ApplicationUtilities {

	/**
	 * Reads a RuneScape style string from the dynamic buffer.
	 * 
	 * @param buffer The dynamic buffer.
	 * 
	 * @return The result of the operation.
	 */
	public static String readRuneScapeString(final ChannelBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		int character;
		while (buffer.readable() && ((character = buffer.readUnsignedByte()) != 10)) {
			builder.append((char) character);
		}
		return builder.toString();
	}
	
	/**
	 * Checks if a object is within the distance of another point.
	 * 
	 * @param alpha The initial point.
	 * 
	 * @param secondary The secondary point.
	 * 
	 * @return The result of the operation.
	 */
	public static int objectWithinDistance(Position alpha, Position secondary) {
		int positionX = secondary.getX() - alpha.getX();
		int positionY = secondary.getY() - alpha.getY();
		return ((int) Math.sqrt(Math.pow(positionX, 2) + Math.pow(positionY, 2)));
	}

	/**
	 * Converts the user-name to a long value.
	 * 
	 * @param context The user-name to be converted.
	 *            
	 * @return the long value to be written.
	 */
	public static long convertNameToLong(String context) {
		long l = 0L;
		for (int i = 0; i < context.length() && i < 12; i++) {
			char c = context.charAt(i);
			l *= 37L;
			if (c >= 'A' && c <= 'Z') {
				l += (1 + c) - 65;
			} else if (c >= 'a' && c <= 'z') {
				l += (1 + c) - 97;
			} else if (c >= '0' && c <= '9') {
				l += (27 + c) - 48;
			}
		}
		while (l % 37L == 0L && l != 0L) {
			l /= 37L;
		}
		return l;
	}
}