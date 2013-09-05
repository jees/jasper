package net.rs.comet.fabric.protocol.codec;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class PacketProtocolMessageEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext context, Channel channel, Object object) throws Exception {
		/*
		 * Writes out all the data currently contained in the internal buffer.
		 */
		PacketBuilder builder = (PacketBuilder) object;
		/*
		 * Returns the message (Contained buffer data).
		 */
		return builder.getBuffer();
	}
}
