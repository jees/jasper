package net.rs.comet.model.game.entity.player.action.impl;

import java.util.concurrent.ScheduledExecutorService;

import net.rs.comet.engine.GameEngine;
import net.rs.comet.engine.GameEngineTask;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.entity.player.action.PlayerAction;

public class TeleportingAction implements PlayerAction {
	
	/**
	 * The new X coordinate.
	 */
	private int teleportX;
	
	/**
	 * The new Y coordinate.
	 */
	private int teleportY;
	
	/**
	 * The new height level plane.
	 */
	private int teleportHeight;
	
	/**
	 * The default class constructor for {@link TeleportingAction}.
	 * 
	 * @param x The new X coordinate.
	 * 
	 * @param y The new Y coordinate.
	 * 
	 * @param z The new height level plane.
	 */
	public TeleportingAction(int x, int y, int z) {
		setTeleportX(x);
		setTeleportY(y);
		setTeleportHeight(z);
	}
	
	/**
	 * Returns the new X coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public int getTeleportX() {
		return teleportX;
	}

	/**
	 * Modifies the new X coordinate.
	 * 
	 * @param teleportX The new modification.
	 */
	public void setTeleportX(int teleportX) {
		this.teleportX = teleportX;
	}

	/**
	 * Returns the new Y coordinate.
	 * 
	 * @return The returned coordinate.
	 */
	public int getTeleportY() {
		return teleportY;
	}

	/**
	 * Modifies the new Y coordinate.
	 * 
	 * @param teleportX The new modification.
	 */
	public void setTeleportY(int teleportY) {
		this.teleportY = teleportY;
	}

	/**
	 * Returns the new height plane.
	 * 
	 * @return The returned plane.
	 */
	public int getTeleportHeight() {
		return teleportHeight;
	}

	/**
	 * Modifies the new height plane.
	 * 
	 * @param teleportHeight The new modification.
	 */
	public void setTeleportHeight(int teleportHeight) {
		this.teleportHeight = teleportHeight;
	}

	@Override
	public void performAction(final Player performer) {
		GameEngine.getSingleton().submitTask(new GameEngineTask() {

			@Override
			public void pulse() {
				performer.setHasTeleported(true);
				performer.performAnimation(65535, 0);
				performer.performAnimation(714, 0);
				performer.activateGraphic(308, 47, true);
				setRunning(false);
			}

			@Override
			public void destruct() {
				stopAction(performer);
			}

			@Override
			public int getTaskTime() {
				return 2100;
			}

			@Override
			public ScheduledExecutorService getService() {
				return GameEngine.getSingleton().getLogistic();
			}	
		});
	}

	@Override
	public void stopAction(Player performer) {
		performer.getPosition().move(getTeleportX(), getTeleportY(), getTeleportHeight());
		performer.performAnimation(715, 0);
	}
}