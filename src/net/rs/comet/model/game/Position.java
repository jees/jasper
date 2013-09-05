package net.rs.comet.model.game;

public class Position {

	/**
	 * The X position of an entity.
	 */
	private short x;

	/**
	 * The Y position of an entity.
	 */
	private short y;

	/**
	 * The height level of an entity.
	 */
	private short z;

	/**
	 * Retrieves the height level of an entity.
	 * 
	 * @return The height level that was retrieved.
	 */
	public int getZ() {
		return (short) z;
	}

	/**
	 * Modifies the height level of an entity.
	 * 
	 * @param positionZ The new modification.
	 */
	public void setZ(int positionZ) {
		this.z = (short) positionZ;
	}

	/**
	 * Retrieves the Y position of an entity.
	 * 
	 * @return The Y position that was retrieved.
	 */
	public int getY() {
		return (short) y;
	}

	/**
	 * Modifies the Y position of an entity.
	 * 
	 * @param positionY The new modification.
	 */
	public void setY(int positionY) {
		this.y = (short) positionY;
	}

	/**
	 * Retrieves the X position of an entity.
	 * 
	 * @return The X position that was retrieved.
	 */
	public int getX() {
		return (short) x;
	}

	/**
	 * Modifies the X position of an entity.
	 * 
	 * @param positionX The new modification.
	 */
	public void setX(int positionX) {
		this.x = (short) positionX;
	}

	/**
	 * Retrieves the region of the players X position.
	 * 
	 * @return The retrieved region.
	 */
	public int getRegionX() {
		return (getX() >> 3) - 6;
	}

	/**
	 * Retrieves the region of the players Y position.
	 * 
	 * @return The retrieved region.
	 */
	public int getRegionY() {
		return (getY() >> 3) - 6;
	}

	/**
	 * Retrieves the region of the X.
	 * 
	 * @return The retrieved region.
	 */
	public int getLocalX() {
		return getLocalX(this);
	}

	/**
	 * Retrieves the region of the Y.
	 * 
	 * @return The retrieved region.
	 */
	public int getLocalY() {
		return getLocalY(this);
	}

	/**
	 * Retrieves the local X for the players region.
	 * 
	 * @param point The point of focus.
	 * 
	 * @return The retrieved position.
	 */
	public int getLocalX(Position point) {
		return getX() - 8 * point.getRegionX();
	}

	/**
	 * Retrieves the local Y for the players region.
	 * 
	 * @param point The point of focus.
	 * 
	 * @return The retrieved position.
	 */
	public int getLocalY(Position point) {
		return getY() - 8 * point.getRegionY();
	}

	/**
	 * Represents the positioning of an entity.
	 * 
	 * @param x The X position of an entity.
	 * 
	 * @param y The Y position of an entity.
	 * 
	 * @param z The height level of an entity.
	 */
	public Position(int x, int y, int z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * Adds a set of values to the position of the entity.
	 * 
	 * @param x The X modification.
	 * 
	 * @param y The Y modification.
	 * 
	 * @param z The height level modification.
	 * 
	 * @return The super type for chaining.
	 */
	public Position set(int x, int y, int z) {
		this.x = (short) (this.x + x);
		this.y = (short) (this.y + y);
		this.z = (short) (this.z + z);
		return this;
	}
	
	/**
	 * Modifies the position of the entity by numeral parameters.
	 * 
	 * @param x The X modification.
	 * 
	 * @param y The Y modification.
	 * 
	 * @param z The height level modification.
	 * 
	 * @return The super type for chaining.
	 */
	public Position move(int x, int y, int z) {
		setX(x);
		setY(y);
		setZ(z);
		return this;
	}
	
	/**
	 * Modifies the {@link Position} of the entity.
	 * 
	 * @param position The new modification.
	 * 
	 * @return The super type for chaining.
	 */
	public Position set(Position position) {
		this.x = (short) position.getX();
		this.y = (short) position.getY();
		this.z = (byte) position.getZ();
		return this;
	}

	/**
	 * Checks if another position is close to our own.
	 * 
	 * @param other The other position.
	 * 
	 * @return The result of the operation.
	 */
	public boolean isWithinDistance(Position other) {
		if(z != other.z) {
			return false;
		}
		int deltaX = other.x - x, deltaY = other.y - y;
		return deltaX <= 14 && deltaX >= -15 && deltaY <= 14 && deltaY >= -15;
	}
}