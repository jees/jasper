package net.rs.comet.model.game.entity.player.action;

import net.rs.comet.model.game.entity.player.Player;

public interface PlayerAction {
	
	/**
	 * Performs an action under fixed circumstances.
	 * 
	 * @param performer The performer.
	 */
	void performAction(Player performer);
	
	/**
	 * Stops an action under fixed circumstances.
	 * 
	 * @param performer The performer.
	 */
	void stopAction(Player performer);
}