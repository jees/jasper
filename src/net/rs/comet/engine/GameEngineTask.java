package net.rs.comet.engine;

import java.util.concurrent.ScheduledExecutorService;

public abstract class GameEngineTask {

	/**
	 * Handles the pulsing of a {@link GameEngineTask}.
	 */
	public abstract void pulse();

	/**
	 * Handles the destruction of a {@link GameEngineTask}.
	 */
	public abstract void destruct();

	/**
	 * Returns the relay time of the executable task.
	 * 
	 * @return The returned time.
	 */
	public abstract int getTaskTime();
	
	/**
	 * The service the {@link GameEngineTask} will condition on.
	 * 
	 * @return The returned service.
	 */
	public abstract ScheduledExecutorService getService();

	/**
	 * If the {@link GameEngineTask} is currently active.
	 */
	private boolean isRunning = true;

	/**
	 * Checks if the {@link GameEngineTask} is currently active.
	 * 
	 * @return The result of the check.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Modifies if {@link GameEngineTask} is currently active.
	 * 
	 * @param isRunning The new modification.
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}