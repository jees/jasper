package net.rs.comet.fabric;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import net.rs.comet.engine.GameEngine;
import net.rs.comet.engine.impl.MobUpdatingTask;
import net.rs.comet.engine.impl.PlayerUpdatingTask;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class ConcurrentGameServerImpl implements ConcurrentGameServer {

	@Override
	public void bindServer(int port, String address) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		/*
		 * Registers the pipeline attributes.
		 */
		bootstrap.setPipelineFactory(new PipelineRegistrationListener());
		/*
		 * Enables Nagle's algorithm for processing connections.
		 */
		bootstrap.setOption("tcpNoDelay", true);
		/*
		 * Keeps the event driven thread alive for the entirety of run time.
		 */
		bootstrap.setOption("keepAlive", true);
		/*
		 * Binds the application to a fixed address.
		 */
		bootstrap.bind(new InetSocketAddress(address, port));
		/*
		 * The application has successfully constructed the networking.
		 */
		System.out.println("The Comet-Game Server Has Bound To The Address: [" + port + "].");
		/*
		 * Begins registering the server's attributes used for processing.
		 */
		this.processServerAttributes();
	}

	@Override
	public void processServerAttributes() {
		/*
		 * The application has begun registering it's tasks.
		 */
		System.out.println("The Comet-Game Login Server has begun processing the global attributes.");
		/*
		 * Submits the player updating task to listen on the engine.
		 */
		GameEngine.getSingleton().submitExternalTask(new PlayerUpdatingTask()); 
		/*
		 * Submits the non-player character updating task to listen on the engine.
		 */
		GameEngine.getSingleton().submitExternalTask(new MobUpdatingTask()); 
	}
}