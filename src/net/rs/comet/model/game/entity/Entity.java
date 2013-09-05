package net.rs.comet.model.game.entity;

import net.rs.comet.model.game.Position;

public abstract class Entity {

	/**
	 * The index of the entity.
	 */
	private int index;
	
	/**
	 * Handles the action of walking.
	 */
	private EntityMovementQueue walking = new EntityMovementQueue(this);
	
	/**
	 * The position of the entity.
	 */
	private Position position;

	/**
	 * The last known position of the player.
	 */
	private Position lastKnownPosition = new Position(0, 0, 0);
	
	/**
	 * The animation index.
	 */
	private int animationIndex;
	
	/**
	 * The animation delay.
	 */
	private int animationDelay;
	
	/**
	 * The graphical index.
	 */
	private int graphicIndex;

	/**
	 * The graphical delay.
	 */
	private int graphicDelay;
	
	/**
	 * If the map region has recently changed.
	 */
	private boolean mapRegionChanging = true;
	
	/**
	 * If an update is required.
	 */
	private boolean updateRequired = true;

	/**
	 * If the entities position has changed drastically.
	 */
	private boolean hasTeleported = false;

	/**
	 * If an appearance update is required.
	 */
	private boolean appearanceUpdateRequired = true;

	/**
	 * If a chat update is required.
	 */
	private boolean chatUpdateRequired = false;
	
	/**
	 * If an animation update is required.
	 */
	private boolean animationUpdateRequired = false;
	
	/**
	 * If a graphical update is required.
	 */
	private boolean graphicUpdateRequired = false;

	/**
	 * The direction of the entity movement.
	 */
	private byte walkingDirection = -1, runningDirection = -1;
	
	/**
	 * Retrieves the index of the entity.
	 * 
	 * @return The index that was retrieved.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Modifies the index of the entity.
	 * 
	 * @param index The new modification to the index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Retrieves the positioning of an entity.
	 * 
	 * @return The retrieved position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Retrieves the last known region of the player.
	 * 
	 * @return The retrieved region.
	 */
	public Position getLastKnownPosition() {
		return lastKnownPosition;
	}

	/**
	 * Modifies the last known position of the player.
	 * 
	 * @param position The new modification.
	 */
	public void setLastKnownPosition(Position position) {
		this.lastKnownPosition = position;
	}
	
	/**
	 * Returns the object used for instancing walking.
	 * 
	 * @return The returned object.
	 */
	public EntityMovementQueue getWalkingQueue() {
		return walking;
	}

	/**
	 * Checks if the entities map region changed.
	 * 
	 * @return The result of the operation.
	 */
	public boolean isMapRegionChanging() {
		return mapRegionChanging;
	}

	/**
	 * Modifies if the entities map region changed.
	 * 
	 * @param mapRegionChanging The new modification.
	 */
	public void setMapRegionChanging(boolean mapRegionChanging) {
		if (mapRegionChanging) {
			setUpdateRequired(true);
		}
		this.mapRegionChanging = mapRegionChanging;
	}

	/**
	 * Checks if the entities position has changed drastically.
	 * 
	 * @return The result of the operation.
	 */
	public boolean isHasTeleported() {
		return hasTeleported;
	}

	/**
	 * Modifies if the entities position has changed drastically.
	 * 
	 * @param hasTeleported The new modification.
	 */
	public void setHasTeleported(boolean hasTeleported) {
		if (hasTeleported) {
			setUpdateRequired(true);
		}
		this.hasTeleported = hasTeleported;
	}


	/**
	 * Retrieves if an appearance update is required.
	 * 
	 * @return The retrieved appearance.
	 */
	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	/**
	 * Modifies if an appearance update is required.
	 * 
	 * @param b The new modification.
	 */
	public void setAppearanceUpdateRequired(boolean b) {
		if (b) {
			setUpdateRequired(true);
		}
		this.appearanceUpdateRequired = b;
	}

	/**
	 * Retrieves the walking direction of the entity.
	 * 
	 * @return The direction that was retrieved.
	 */
	public byte getWalkingDirection() {
		return walkingDirection;
	}

	/**
	 * Modifies the walking direction of the entity.
	 * 
	 * @param walkingDirection The new modification.
	 */
	public void setWalkingDirection(byte walkingDirection) {
		this.walkingDirection = walkingDirection;
	}

	/**
	 * Retrieves the running direction of the entity.
	 * 
	 * @return The direction that was retrieved.
	 */
	public byte getRunningDirection() {
		return runningDirection;
	}

	/**
	 * Modifies the running direction of the entity.
	 * 
	 * @param runningDirection The new modification.
	 */
	public void setRunningDirection(byte runningDirection) {
		this.runningDirection = runningDirection;
	}

	/**
	 * Checks if a chat update is required.
	 * 
	 * @return The result.
	 */
	public boolean isChatUpdateRequired() {
		return chatUpdateRequired;
	}

	/**
	 * Modifies if a chat update is required.
	 * 
	 * @param chatUpdateRequired The new modification.
	 */
	public void setChatUpdateRequired(boolean chatUpdateRequired) {
		if (chatUpdateRequired) {
			setUpdateRequired(true);
		}
		this.chatUpdateRequired = chatUpdateRequired;
	}
	
	/**
	 * Modifies the position of the player.
	 * 
	 * @param position The new modification.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Modifies the directions an entity is currently moving.
	 * 
	 * @param walking The walking direction.
	 * 
	 * @param running The running direction.
	 */
	public void setMovementDirections(int walking, int running) {
		setWalkingDirection((byte) walking);
		setRunningDirection((byte) running);
	}

	/**
	 * Modifies if an update is required.
	 * 
	 * @param updateRequired The new modification.
	 */
	public void setUpdateRequired(boolean updateRequired) {
		this.updateRequired = updateRequired;
	}
	
	/**
	 * Checks if an update was required.
	 * 
	 * @return The required update.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	/**
	 * Returns the animation index of the entity.
	 * 
	 * @return The returned index.
	 */
	public int getAnimationIndex() {
		return animationIndex;
	}

	/**
	 * Modifies the animation index of the entity.
	 * 
	 * @param animationIndex The new modification.
	 */
	public void setAnimationIndex(int animationIndex) {
		this.animationIndex = animationIndex;
	}

	/**
	 * Returns the graphical index of the entity.
	 * 
	 * @return The returned index.
	 */
	public int getGraphicIndex() {
		return graphicIndex;
	}

	/**
	 * Modifies the graphical index of the entity.
	 * 
	 * @param graphicIndex The new modification.
	 */
	public void setGraphicIndex(int graphicIndex) {
		this.graphicIndex = graphicIndex;
	}

	/**
	 * Returns the animation delay.
	 * 
	 * @return The returned delay.
	 */
	public int getAnimationDelay() {
		return animationDelay;
	}

	/**
	 * Modifies the animation delay.
	 * 
	 * @param animationDelay The new modification.
	 */
	public void setAnimationDelay(int animationDelay) {
		this.animationDelay = animationDelay;
	}

	/**
	 * Returns the graphical delay.
	 * 
	 * @return The returned delay.
	 */
	public int getGraphicDelay() {
		return graphicDelay;
	}

	/**
	 * Modifies the graphical delay.
	 * 
	 * @param graphicDelay The new modification.
	 */
	public void setGraphicDelay(int graphicDelay) {
		this.graphicDelay = graphicDelay;
	}
	
	/**
	 * Performs an animation.
	 * 
	 * @param animationIndex The animation index.
	 * 
	 * @param animationDelay The animation delay.
	 */
	public void performAnimation(int animationIndex, int animationDelay) {
		setAnimationIndex(animationIndex);
		setAnimationDelay(animationDelay);
		setAnimationUpdateRequired(true);
	}
	
	/**
	 * Activates a graphic.
	 * 
	 * @param graphicIndex The graphic index.
	 * 
	 * @param graphicDelay The graphic delay.
	 * 
	 * @param chest If the graphic is performed at the entities chest.
	 */
	public void activateGraphic(int graphicIndex, int graphicDelay, boolean chest) {
		setGraphicIndex(graphicIndex);
		if (chest) {
			setGraphicDelay(graphicDelay + + 6553600);
		} else {
			setGraphicDelay(graphicDelay);
		}
		setGraphicUpdateRequired(true);
	}

	/**
	 * Checks if an animation update is required.
	 * 
	 * @return The result.
	 */
	public boolean isAnimationUpdateRequired() {
		return animationUpdateRequired;
	}

	/**
	 * Modifies if an animation update is required.
	 * 
	 * @param animationUpdateRequired The new modification.
	 */
	public void setAnimationUpdateRequired(boolean animationUpdateRequired) {
		if (animationUpdateRequired) {
			setUpdateRequired(true);
		}
		this.animationUpdateRequired = animationUpdateRequired;
	}

	/**
	 * Checks if a graphical update is required.
	 * 
	 * @return The result.
	 */
	public boolean isGraphicUpdateRequired() {
		return graphicUpdateRequired;
	}

	/**
	 * Modifies if a graphical update is required.
	 * 
	 * @param graphicUpdateRequired The new modification.
	 */
	public void setGraphicUpdateRequired(boolean graphicUpdateRequired) {
		if (graphicUpdateRequired) {
			setUpdateRequired(true);
		}
		this.graphicUpdateRequired = graphicUpdateRequired;
	}
}