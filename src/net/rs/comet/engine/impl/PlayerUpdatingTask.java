package net.rs.comet.engine.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import net.rs.comet.engine.GameEngineTask;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.mob.MobUpdatingProcedure;
import net.rs.comet.model.game.entity.player.Player;

public class PlayerUpdatingTask extends GameEngineTask {

	@Override
	public void pulse() {
		for (Player player: Game.getSingleton().getPlayers().keySet()) {
			if (player != null) {
				player.getUpdate().performPreUpdate();
			}
		}
		for (Player player: Game.getSingleton().getPlayers().keySet()) {
			if (player != null) {
				/*
				 * The main updating procedure for the players in the virtual world.
				 */
				player.getUpdate().performUpdate();
				/*
				 * The main updating procedure for the non-player characters in the 
				 * virtual world.
				 */
				MobUpdatingProcedure.updateNonPlayerCharacter(player);
			}
		}
		for (Player player: Game.getSingleton().getPlayers().keySet()) {
			if (player != null) {
				player.getUpdate().performPostUpdate();
			}
		}
	}

	@Override
	public void destruct() {
		/*
		 * If this task dies at any point the application will shut-down.
		 */
		System.exit(0);
	}

	@Override
	public int getTaskTime() {
		return 600;
	}

	@Override
	public ScheduledExecutorService getService() {
		/*
		 * This task listens on it's own thread since its a high latency procedure.
		 */
		return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() : 1);
	}
}