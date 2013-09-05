package net.rs.comet.model.game.object;

import java.util.ArrayList;

import net.rs.comet.fabric.protocol.packet.encode.impl.SendGameObjectPacketEncoder;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.utilities.ApplicationUtilities;

public class GameObjectHandler {
	
	/**
	 * A compaction of all the global objects in the virtual world.
	 */
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	/**
	 * The singleton pattern implementation.
	 */
	private static GameObjectHandler singleton;
	
	/**
	 * Returns the singleton pattern implementation.
	 * 
	 * @return The returned pattern.
	 */
	public static GameObjectHandler getSingleton() {
		if (singleton == null) {
			singleton = new GameObjectHandler();
		}
		return singleton;
	}
	
	/**
	 * Sends a global object to all the players within the region.
	 * 
	 * @param player The {@link Player} in the region.
	 * 
	 * @param object The object in the region.
	 */
	public void sendGlobalObject(Player player, GameObject object) {
		getObjects().add(object);
		sendLocalObjects();
	}
	
	/**
	 * Creates an object that only the activator can see.
	 * 
	 * @param player The activator.
	 * 
	 * @param object The private object.
	 */
	public void sendPrivateObject(Player player, GameObject object) {
		player.writePacketMessage(new SendGameObjectPacketEncoder(object));
	}
	
	/**
	 * Updates the local objects of a {@link Player}.
	 * 
	 * @param player The {@link Player} trigger.
	 */
	public void sendLocalObjects() {
		for (Player otherPlayer : Game.getSingleton().getPlayers().keySet()) {
			if (otherPlayer == null) {
				continue;
			}
			for (GameObject localObject : getObjects()) {
				if (ApplicationUtilities.objectWithinDistance(otherPlayer.getPosition(), localObject.getPosition()) <= 15) {
					otherPlayer.writePacketMessage(new SendGameObjectPacketEncoder(localObject));
				}
			}
		}
	}

	/**
	 * Returns the objects that have been loaded.
	 * 
	 * @return The object array.
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	/**
	 * Modifies the objects that have been loaded.
	 * 
	 * @param objects The new modification.
	 */
	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

	/**
	 * Modifies the singleton pattern implementation.
	 * 
	 * @param singleton The new modification.
	 */
	public static void setSingleton(GameObjectHandler singleton) {
		GameObjectHandler.singleton = singleton;
	}
	
	/**
	 * The default class constructor for {@link GameObjectHandler}.
	 */
	public GameObjectHandler() { 
		
	}
}