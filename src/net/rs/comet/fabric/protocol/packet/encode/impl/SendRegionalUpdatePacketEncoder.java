package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendRegionalUpdatePacketEncoder implements PacketProtocolMessageEncoder {

	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		player.getLastKnownPosition().set(player.getPosition());
		builder.writeRawPacketHeader(player.getEncoder(), 73);
		builder.writeAdditionalShort(player.getPosition().getRegionX() + 6);
		builder.writeShort((short) (player.getPosition().getRegionY() + 6));
		player.getChannel().write(builder);
	}
}