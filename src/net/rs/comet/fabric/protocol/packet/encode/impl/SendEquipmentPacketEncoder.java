package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class SendEquipmentPacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The slot the item will be sent to.
	 */
	private int slot;
	
	/**
	 * The identity number of the item.
	 */
	private int identity;
	
	/**
	 * The amount of the item.
	 */
	private int amount;
	
	/**
	 * The default class constructor for {@link SendEquipmentPacketEncoder}.
	 * 
	 * @param slot The slot.
	 * 
	 * @param identity The identity.
	 * 
	 * @param amount The amount.
	 */
	public SendEquipmentPacketEncoder(int slot, int identity, int amount) {
		this.slot = slot;
		this.identity = identity;
		this.amount = amount;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeVariableShortPacketHeader(player.getEncoder(), 34);
		builder.writeShort((short) 1688);
		builder.writeByte((byte) slot);
		builder.writeShort((short) (identity + 1));
		if (amount > 254) {
			builder.writeByte((byte) 255);
			builder.writeShort((short) amount);
		} else {
			builder.writeByte((byte) amount);
		}
		builder.finishVariableShortPacketHeader();
		player.getChannel().write(builder);
	}
}