package net.rs.comet.engine;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {

	/**
	 * The singleton pattern implementation to avoid static access.
	 */
	private static GameEngine singleton;
	
	/**
	 * The executable {@link GameEngineTask} logistic. This is what low latency tasks
	 * should return as their service.
	 */
	private ScheduledExecutorService logistic = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() : 1);

	/**
	 * A compaction of all the tasks in activity.
	 */
	private Queue<GameEngineTask> taskCompaction = new LinkedList<GameEngineTask>();

	/**
	 * Submits an {@link GameEngineTask} for pulsing.
	 * 
	 * @param task The {@link GameEngineTask} being submitted.
	 */
	public void submitExternalTask(GameEngineTask task) {
		submitTask(task);
	}

	/**
	 * Returns the singleton pattern implementation.
	 * 
	 * @return The returned pattern implementation.
	 */
	public static GameEngine getSingleton() {
		if (singleton == null) {
			singleton = new GameEngine();
		}
		return singleton;
	}
	
	/**
	 * Returns the executable logistic's service.
	 * 
	 * @return The returned logistic's service.
	 */
	public ScheduledExecutorService getLogistic() {
		return logistic;
	}

	/**
	 * Modifies the executable logistic's service.
	 * 
	 * @param logisitic The new modification.
	 */
	public void setLogistic(ScheduledExecutorService logisitic) {
		this.logistic = logisitic;
	}
	
	/**
	 * Returns a compaction of all the active tasks.
	 * 
	 * @return The returned compaction.
	 */
	public Queue<GameEngineTask> getTaskCompaction() {
		return taskCompaction;
	}

	/**
	 * Modifies a compaction of all the active tasks.
	 * 
	 * @param taskCompaction The new modification.
	 */
	public void setTaskCompaction(Queue<GameEngineTask> taskCompaction) {
		this.taskCompaction = taskCompaction;
	}

	/**
	 * Submits a new {@link GameEngineTask} for processing.
	 * 
	 * @param task The {@link GameEngineTask} being submitted.
	 */
	public void submitTask(final GameEngineTask task) {
		/*
		 * Adds the new task into the concurrent compaction.
		 */
		getTaskCompaction().add(task);
		/*
		 * Utilizes the task's service to execute it at a fixed rate.
		 */
		task.getService().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (getTaskCompaction().contains(task)) {
					synchronized(task) {
						if (task.isRunning()) {
							task.pulse();
						} else {
							task.destruct();
							getTaskCompaction().remove(task);
						}
					}
				}
			}
		}, 0, task.getTaskTime(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * The default class constructor for {@link GameEngine}.
	 */
	public GameEngine() { 
		
	}
}