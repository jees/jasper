package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.item.Item;

public class SendInitialPacketEncoder implements PacketProtocolMessageEncoder {

	@Override
	public void encodePacket(Player player) {
		/*
		 * The packet for writing the initial login details.
		 */
		PacketBuilder builder = new PacketBuilder();
		builder.writeRawPacketHeader(player.getEncoder(), 249);
		/*
		 * If the player is a member.
		 */
		builder.writeByte((byte) (1 + 128));
		/*
		 * The index of the player.
		 */
		builder.writeAdditionalLittleEndianShort(player.getIndex());
		player.getChannel().write(builder);
		/*
		 * Sends the visual attributes and modifies the variables to starting position.
		 */
		int[] sidebars = { 2423, 3917, 638, 3213, 1644, 5608, 1151, -1, 5065, 5715, 2449, 904, 147, 962 };
		for (int i = 0; i < sidebars.length; i++) {
			player.writePacketMessage(new SendSidebarPacketEncoder(i, sidebars[i]));
		}
		for (int i = 0; i < 28; i ++) {
			player.getInventory()[i] = new Item(-1, 0);
		}
		player.writePacketMessage(new SendInventoryUpdatePacketEncoder(player.getInventory(), 3214));
		player.writePacketMessage(new SendConfigurationPacketEncoder(173, 0));
		player.writePacketMessage(new SendChatMessagePacketEncoder("Welcome to Comet."));
		player.writePacketMessage(new SendStringPacketEncoder(149, player.getEnergyLeft() + "%"));
		player.writePacketMessage(new SendInterfacePacketEncoder(3559));
	}
}