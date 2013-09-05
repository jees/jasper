package net.rs.comet.fabric.protocol.packet.encode.impl;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.item.Item;

public class SendInventoryUpdatePacketEncoder implements PacketProtocolMessageEncoder {

	/**
	 * The items in the players inventory.
	 */
	private Item[] items;
	
	/**
	 * The interface.
	 */
	private int inventory;
	
	/**
	 * The default class constructor.
	 * 
	 * @param itemsThe items in the players inventory.
	 * 
	 * @param inventory The interface.
	 */
	public SendInventoryUpdatePacketEncoder(Item[] items, int inventory) {
		this.items = items;
		this.inventory = inventory;
	}
	
	@Override
	public void encodePacket(Player player) {
		PacketBuilder builder = new PacketBuilder();
		builder.writeVariableShortPacketHeader(player.getEncoder(), 53);
		builder.writeShort((short) inventory);
		builder.writeShort((short) items.length);
		for (Item item : items) {
			if (item != null) {
				if (item.getItemAmount() > 254) {
					builder.writeByte((byte) 255);
					builder.writeInteger1(item.getItemAmount());
				} else {
					builder.writeByte((byte) item.getItemAmount());
				}
				builder.writeAdditionalLittleEndianShort(item.getItemIdentity() + 1);
			} else {
				builder.writeByte((byte) 0);
				builder.writeLittleEndianShort(0);
			}
		}
		builder.finishVariableShortPacketHeader();
		player.getChannel().write(builder);
	}
}