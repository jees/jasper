package net.rs.comet.model.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.rs.comet.model.game.entity.mob.Mob;
import net.rs.comet.model.game.entity.player.Player;

public class Game {

	/**
	 * Holds a concurrent compaction of the  players in the virtual world.
	 */
	private Map<Player, String> players = new ConcurrentHashMap<Player, String>();
	
	/**
	 * Holds a compaction of the  NPC's in the virtual world.
	 */
	private Map<Mob, Integer> mob = new ConcurrentHashMap<Mob, Integer>();
	
	/**
	 * The singleton pattern implementation to avoid static access.
	 */
	private static Game singleton;
	
	/**
	 * Returns the singleton pattern implementation.
	 * 
	 * @return The returned singleton.
	 */
	public static Game getSingleton() {
		if (singleton == null) {
			singleton = new Game();
		}
		return singleton;
	}

	/**
	 * Retrieves the players held in the concurrent compaction.
	 * 
	 * @return The retrieved players.
	 */
	public Map<Player, String> getPlayers() {
		return players;
	}

	/**
	 * Modifies the players held in the concurrent compaction.
	 * 
	 * @param players The new modification.
	 */
	public void setPlayers(Map<Player, String> players) {
		this.players = players;
	}
	
	/**
	 * Returns a compaction of the mobs in the virtual world.
	 * 
	 * @return The returned compaction.
	 */
	public Map<Mob, Integer> getMobs() {
		return mob;
	}

	/**
	 * Modifies a compaction of the mobs in the virtual world.
	 * 
	 * @param npcs The new modification.
	 */
	public void setMobs(Map<Mob, Integer> npcs) {
		this.mob = npcs;
	}

	/**
	 * Registers a new player into the virtual world.
	 * 
	 * @param entity The player being registered.
	 */
	public void registerPlayer(Player player) {
		getPlayers().put(player, player.getAccountName());
		player.setIndex(players.size());
	}
	
	/**
	 * Registers a new {@link Mob} into the virtual world.
	 * 
	 * @param npc The {@link Mob} being registered.
	 */
	public void registerMob(Mob npc) {
		getMobs().put(npc, npc.getIndex());
		npc.setIndex(mob.size());
	}

	/**
	 * Removes a player from the virtual world.
	 * 
	 * @param player The player that was removed.
	 */
	public void removePlayer(Player player) {
		getPlayers().remove(player);
		player.setLoggedIn(false);
	}
	
	/**
	 * Removes a mob from the virtual world.
	 * 
	 * @param mob The mob that was removed.
	 */
	public void removeMob(Mob mob) {
		getMobs().remove(mob);
	}
	
	/**
	 * The default class constructor for {@link Game}.
	 */
	public Game() {  
		
	}
}