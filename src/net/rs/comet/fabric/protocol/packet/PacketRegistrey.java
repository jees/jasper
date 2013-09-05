package net.rs.comet.fabric.protocol.packet;

import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.ActionButtonPacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.EquipItemPacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.ModifyAppearancePacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.RegionalUpdatePacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.CommandPacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.CommunicationPacketDecoder;
import net.rs.comet.fabric.protocol.packet.decode.impl.MovementPacketDecoder;
import net.rs.comet.model.game.entity.player.Player;

public class PacketRegistrey {

	/**
	 * Holds all of the statically initialized packets.
	 */
	private static PacketProtocolMessageDecoder[] packets = new PacketProtocolMessageDecoder[256];

	/**
	 * Statically initializes the packets.
	 */
	public static void loadPackets() {
		/*
		 * Populates the packet array with the specified indexes.
		 */
		packets[185] = new ActionButtonPacketDecoder();
		packets[103] = new CommandPacketDecoder();
		packets[4] = new CommunicationPacketDecoder();
		packets[164] = new MovementPacketDecoder();
		packets[98] = new MovementPacketDecoder();
		packets[248] = new MovementPacketDecoder();
		packets[101] = new ModifyAppearancePacketDecoder();
		packets[121] = new RegionalUpdatePacketDecoder();
		packets[41] = new EquipItemPacketDecoder();
	}

	/**
	 * Handles the incoming packet transmission.
	 * 
	 * @param player The player association.
	 * 
	 * @param packet The packet of interest.
	 */
	public static void readPacket(Player player, Packet packet) {
		if (packets[packet.getOpcode()] != null) {
			packets[packet.getOpcode()].decodePacket(packet, player);
		}
	}

	/**
	 * Retrieves the array holding the loaded packets.
	 * 
	 * @return The retrieved array.
	 */
	public PacketProtocolMessageDecoder[] getPackets() {
		return packets;
	}

	/**
	 * Modifies the array holding the loaded packets.
	 * 
	 * @param packets The new modification.
	 */
	public void setPackets(PacketProtocolMessageDecoder[] packets) {
		PacketRegistrey.packets = packets;
	}
}