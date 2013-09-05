package net.rs.comet.engine.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import net.rs.comet.engine.GameEngineTask;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.mob.Mob;
import net.rs.comet.model.game.entity.mob.MobUpdatingProcedure;

public class MobUpdatingTask extends GameEngineTask {

	@Override
	public void pulse() {
		for (Mob mob: Game.getSingleton().getMobs().keySet()) {
			if (mob != null) {
				MobUpdatingProcedure.preUpdate(mob);
			}
		}
		for (Mob mob: Game.getSingleton().getMobs().keySet()) {
			if (mob != null) {
				MobUpdatingProcedure.postUpdate(mob);
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