package org.jasper.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jasper.rs2.GameConstants;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

/**
 * Binds all our networking using {@link ServerBootstrap}
 * @author Ares_
 *
 */
public final class GameServerBootstrap {

	/**
	 * The {@link ServerBootstrap} for binding our networking.
	 */
	private ServerBootstrap bootstrap = new ServerBootstrap();
	
	/**
	 * The {@link ExecutorService} and{@link Executors} for our networking.
	 */
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	/**
	 * The {@link Logger} instance for the {@link GameServerBootstrap} class.
	 */
	private Logger logger = Logger.getLogger(GameServerBootstrap.class.getName());
	
	/**
	 * Constructs a new {@link GameServerBootstrap}
	 */
	public GameServerBootstrap() {
		
	}
	
	/**
	 * Bind the networking
	 */
	public void bind() {
		logger.info("Starting to bind the networking...");
		
		// start setting up the netty networking
		bootstrap.setFactory(new NioServerSocketChannelFactory(executor, executor));
		bootstrap.setPipelineFactory(new GameChannelPipelineFactory(new HashedWheelTimer()));
	
		// The address the server will be listening too
		SocketAddress game = new InetSocketAddress(GameConstants.PORT);
	
		logger.info("The game server is now listening to " +game+ ".");
		bootstrap.bind(game);
	}
	
}