package org.jasper.net;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.Timer;

/**
 * A {@link ChannelPipelineFactory} which creates {@link ChannelPipeline} for the pipeline.
 * @author Ares_
 *
 */
public final class GameChannelPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The {@link Timer} for connections timing out
	 */
	private final Timer timer;
	
	/**
	 * Constructs a new {@link GameChannelPipelineFactory} with a {@link Timer}
	 * @param timer The timer for connections
	 */
	GameChannelPipelineFactory(Timer timer) {
		this.timer = timer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline p = Channels.pipeline();
		p.addLast("timeout", new ReadTimeoutHandler(timer, 10));
		return p;
	}

}