package net.rs.comet.model.game.object;

import net.rs.comet.model.game.Position;

public class GameObject {
	
	/**
	 * The index of the object.
	 */
	private int index;
	
	/**
	 * The numerical index type of the object.
	 */
	private int indexType;
	
	/**
	 * The numerical rotation of the object.
	 */
	private int rotation;
	
	/**
	 * The {@link Position} of the object.
	 */
	private Position position;
	
	/**
	 * Constructs a new {@link GameObject}.
	 * 
	 * @param index The index of the object.
	 * 
	 * @param indexType The numeral index type of the object.
	 * 
	 * @param rotation The numeral rotation of the object.
	 * 
	 * @param position The object position.
	 */
	public GameObject(int index, int indexType, int rotation, Position position) {
		setIndex(index);
		setIndexType(indexType);
		setRotation(rotation);
		setPosition(position);
	}

	/**
	 * Returns the index of the object.
	 * 
	 * @return The returned index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Modifies the index of the object.
	 * 
	 * @param index The new modification.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Returns the numerical index type of the object.
	 * 
	 * @return The returned index.
	 */
	public int getIndexType() {
		return indexType;
	}

	/**
	 * Modifies the numerical index of the object.
	 * 
	 * @param indexType The new modification.
	 */
	public void setIndexType(int indexType) {
		this.indexType = indexType;
	}

	/**
	 * Returns the numerical rotation of the object.
	 * 
	 * @return The returned rotation.
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * Modifies the numerical rotation of the object.
	 * 
	 * @param rotation The new modification.
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	/**
	 * Returns the {@link Position} of the object.
	 * 
	 * @return The returned {@link Position}.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Modifies the {@link Position} of the object.
	 * 
	 * @param position The new modification.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
}