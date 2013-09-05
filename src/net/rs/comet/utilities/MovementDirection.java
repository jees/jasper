package net.rs.comet.utilities;

public class MovementDirection {

	/**
	 * An array of the possible directions of the delta X.
	 */
	public static final byte[] DIRECTION_DELTA_X = new byte[] { -1, 0, 1, -1, 1, -1, 0, 1 };

	/**
	 * An array of the possible directions of the delta Y.
	 */
	public static final byte[] DIRECTION_DELTA_Y = new byte[] { 1, 1, 1, 0, 0, -1, -1, -1 };

	/**
	 * Returns the next direction in the algorithm.
	 * 
	 * @param deltaX The delta X position.
	 * 
	 * @param deltaY The delta Y position.
	 * 
	 * @return The result of the operation.
	 */
	public static int parseDirection(int deltaX, int deltaY) {
		if (deltaX < 0) {
			if (deltaY < 0) {
				return 5;
			} else if (deltaY > 0) {
				return 0;
			} else {
				return 3;
			}
		} else if (deltaX > 0) {
			if (deltaY < 0) {
				return 7;
			} else if (deltaY > 0) {
				return 2;
			} else {
				return 4;
			}
		} else {
			if (deltaY < 0){
				return 6;
			} else if (deltaY > 0) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}