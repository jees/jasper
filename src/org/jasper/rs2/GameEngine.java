package org.jasper.rs2;

/**
 * The game engine to load background tasks
 * @author Ares_
 *
 */
public final class GameEngine {

	/**
	 * The {@link GameEngine} singleton
	 */
	private static final GameEngine ENGINE = new GameEngine();
	
	/**
	 * Creates a new {@link GameEngine}
	 */
	public GameEngine() {
		
	}
	
	/**
	 * Gets the game engine instance
	 * @return The game engine
	 */
	public GameEngine getEngine() {
		return ENGINE;
	}
	
}