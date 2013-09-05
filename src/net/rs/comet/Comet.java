package net.rs.comet;

import net.rs.comet.fabric.ConcurrentGameServer;
import net.rs.comet.fabric.ConcurrentGameServerImpl;
import net.rs.comet.fabric.protocol.packet.PacketRegistrey;
import net.rs.comet.model.game.entity.mob.MobSpawnLoader;
import net.rs.comet.model.game.object.GameObjectLoader;
import net.rs.comet.utilities.ApplicationConstants;

public class Comet {
 //TRY THIS
	/**
	 * The current loading stage of the application.
	 */
	private static LoadingStage stage = LoadingStage.LOAD_DATA;

	/**
	 * The possible loading stages of the start-up procedure.
	 */
	private enum LoadingStage { 
		
		/*
		 * Loads any outside or external data into the application.
		 */
		LOAD_DATA, 
		
		/*
		 * Activates the networking system backing the application. 
		 */
		NETWORKING, 

		/*
		 * Completes the initialization procedure by finalizing any needs.
		 */
		COMPLETED 
	}

	/**
	 * The entry point of the application.
	 * 
	 * @param commandLine The command line arguments.
	 * 
	 * @throws Exception The exception thrown if an error occurs.
	 */
	public static void main(String commandLine[]) throws Exception {
		switch (stage) {

		case LOAD_DATA:
			MobSpawnLoader.loadSpawns();
			GameObjectLoader.loadObjects();
			PacketRegistrey.loadPackets();

		case NETWORKING:
			ConcurrentGameServer server = new ConcurrentGameServerImpl();
			server.bindServer(ApplicationConstants.SERVER_PORT,ApplicationConstants.SERVER_ADDRESS);

		case COMPLETED:
			System.out.println("The Comet-Game Server has successfully started and is ready for connections.");
			break; 
		}
	}
}