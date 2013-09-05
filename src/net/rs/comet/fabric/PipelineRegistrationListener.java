package net.rs.comet.fabric;

import net.rs.comet.fabric.protocol.codec.LoginProtocolDecoder;
import net.rs.comet.fabric.protocol.codec.PacketProtocolMessageEncoder;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class PipelineRegistrationListener implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		/*
		 * The chain the encoders and decoders will be registered alongside.
		 */
		final ChannelPipeline pipeline = Channels.pipeline();
		/*
		 * Handles the encoding of outgoing packets and responses.
		 */
		pipeline.addLast("encoder", new PacketProtocolMessageEncoder());
		/*
		 * Handles the decoding of incoming packets and responses.
		 */
		pipeline.addLast("decoder", new LoginProtocolDecoder());
		/*
		 * The result of the chain registration.
		 */
		return pipeline;
	}  
}