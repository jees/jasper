package net.rs.comet.model.game.entity;

import java.util.Deque;
import java.util.LinkedList;

import net.rs.comet.fabric.protocol.packet.encode.impl.SendConfigurationPacketEncoder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendStringPacketEncoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.utilities.MovementDirection;

public class EntityMovementQueue {
	
	private static class Point {

		/**
		 * The X position of the point.
		 */
		private final int pointX;
		
		/**
		 * The Y position of the point.
		 */
		private final int pointY;
		
		/**
		 * The direction of the point.
		 */
		private final int pointDirection;

		/**
		 * The statically enabled default class constructor for {@link Point}.
		 * 
		 * @param x The X position.
		 * 
		 * @param y The Y position.
		 * 
		 * @param dir The direction.
		 */
		public Point(int x, int y, int dir) {
			this.pointX = x;
			this.pointY = y;
			this.pointDirection = dir;
		}
	}

	/**
	 * The entity associated with the concurrent movement.
	 */
	private Entity entity;
	
	/**
	 * The way points in the movement queue.
	 */
	private Deque<Point> availableWaypoints = new LinkedList<Point>();
	
	/**
	 * If an entity is currently running.
	 */
	private boolean isRunning = false, runQueue = false;
	
	/**
	 * The restoration timer for restoring energy.
	 */
	private int restorationTimer;

	/**
	 * The default class constructor for {@link EntityMovementQueue}.
	 * 
	 * @param entity The entity.
	 */
	public EntityMovementQueue(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * Finished the movement operation.
	 */
	public void finish() {
		availableWaypoints.removeFirst();
	}

	/**
	 * Checks if the Dequeue is empty.
	 * 
	 * @return The result of the check.
	 */
	public boolean isEmpty() {
		return availableWaypoints.size() == 0;
	}

	/**
	 * Modifies if the entity is currently running.
	 * 
	 * @param isRunning The new modification.
	 */
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * Modifies is the running queue is active.
	 * 
	 * @param runQueue The new modification.
	 */
	public void setRunQueue(boolean runQueue) {
		this.runQueue = runQueue;
	}

	/**
	 * Checks if the entity is currently running.
	 * 
	 * @return The result of the check.
	 */
	public boolean isRunning() {
		return isRunning || runQueue;
	}

	/**
	 * Checks if the running queue is active.
	 * 
	 * @return The result of the check.
	 */
	public boolean runQueueEnabled() {
		return runQueue;
	}

	/**
	 * Returns the current value of the restoration timer.
	 * 
	 * @return The returned value.
	 */
	public int getRestorationTimer() {
		return restorationTimer;
	}

	/**
	 * Modifies the current value of the restoration timer.
	 * 
	 * @param restorationTimer The new modification.
	 */
	public void setRestorationTimer(int restorationTimer) {
		this.restorationTimer = restorationTimer;
	}

	/**
	 * Resets the processed attributes.
	 */
	public void reset() {
		runQueue = false;
		availableWaypoints.clear();
		availableWaypoints.add(new Point(entity.getPosition().getX(), entity.getPosition().getY(), -1));
	}

	/**
	 * Adds a singular step into the walking dequeue.
	 * 
	 * @param x The X position of the step.
	 * 
	 * @param y The Y position of the step.
	 */
	public void addExternalStep(int x, int y) {
		if (isEmpty()) {
			reset();
		}
		Point lastRecordedLocation = availableWaypoints.peekLast();
		int XDifference = x - lastRecordedLocation.pointX;
		int YDifference = y - lastRecordedLocation.pointY;
		int max = Math.max(Math.abs(XDifference), Math.abs(YDifference));
		for (int i = 0; i < max; i++) {
			if (XDifference < 0) {
				XDifference++;
			} else if (XDifference > 0) {
				XDifference--;
			}
			if (YDifference < 0) {
				YDifference++;
			} else if (YDifference > 0) {
				YDifference--;
			}
			addInternalStep(x - XDifference, y - YDifference);
		}
	}

	/**
	 * Adds an internal step into the Dequeue.
	 * 
	 * @param x The X position of the internal step.
	 * 
	 * @param y The Y position of the internal step.
	 */
	private void addInternalStep(int x, int y) {
		if (availableWaypoints.size() >= 50) {
			return;
		}
		Point lastRecordedPosition = availableWaypoints.peekLast();
		int XDifference = x - lastRecordedPosition.pointX;
		int YDifference = y - lastRecordedPosition.pointY;
		int direction = MovementDirection.parseDirection(XDifference, YDifference);
		if (direction > -1) {
			availableWaypoints.add(new Point(x, y, direction));
		}
	}

	/**
	 * Returns the next point directed in the walking Dequeue.
	 * 
	 * @return The result of the operation.
	 */
	private Point returnNextValidPoint() {
		Point availableWaypoint = availableWaypoints.poll();
		if (availableWaypoint == null || availableWaypoint.pointDirection == -1) {
			return null;
		} else {
			int XDifference = MovementDirection.DIRECTION_DELTA_X[availableWaypoint.pointDirection];
			int YDifference = MovementDirection.DIRECTION_DELTA_Y[availableWaypoint.pointDirection];
			entity.getPosition().set(XDifference, YDifference, entity.getPosition().getZ());
			return availableWaypoint;
		}
	}
	
	/**
	 * Processes the next movement of the entity.
	 */
	public void updateNextMovement() {
		Point walkingPoint = null, runningPoint = null;
		if (entity.isHasTeleported()) {
			reset();
		} else {
			walkingPoint = returnNextValidPoint();
			Player player = (Player) entity;
			if (isRunning()) {
				runningPoint = returnNextValidPoint();
				if (entity instanceof Player) {
					if (runningPoint != null) {
						if (player.getEnergyLeft() > 0 && player.getWalkingQueue().isRunning()) {
							player.setEnergyLeft(player.getEnergyLeft() - 1);
							player.writePacketMessage(new SendStringPacketEncoder(149, player.getEnergyLeft() + "%"));
						}
					}
					if (player.getEnergyLeft() == 0) {
						player.getWalkingQueue().setIsRunning(false);
						player.getWalkingQueue().setRunQueue(false);
						player.writePacketMessage(new SendConfigurationPacketEncoder(173, 0));
					}
				}
				if (player.getEnergyLeft() < 100 && runningPoint == null || player.getEnergyLeft() < 100 && walkingPoint == null || player.getEnergyLeft() < 100 && walkingPoint != null) {
					if (player.getWalkingQueue().getRestorationTimer() < 5) {
						player.getWalkingQueue().setRestorationTimer(player.getWalkingQueue().getRestorationTimer() + 1);
					} else if (player.getWalkingQueue().getRestorationTimer() >= 5) {
						player.setEnergyLeft(player.getEnergyLeft() + 1);
						player.getWalkingQueue().setRestorationTimer(0);
						player.writePacketMessage(new SendStringPacketEncoder(149, player.getEnergyLeft() + "%"));
					}
				}
			}
			int walkingDirection = walkingPoint == null ? -1 : walkingPoint.pointDirection;
			int runningDirection = runningPoint == null ? -1 : runningPoint.pointDirection;
			entity.setMovementDirections(walkingDirection, runningDirection);
		}
		int XDifference = entity.getPosition().getX() - entity.getLastKnownPosition().getRegionX() * 8;
		int YDifference = entity.getPosition().getY() - entity.getLastKnownPosition().getRegionY() * 8;
		boolean state = XDifference < 16 || XDifference >= 88 || YDifference < 16 || YDifference > 88;
		if (state) {
			entity.setMapRegionChanging(state);
		}
	}

}