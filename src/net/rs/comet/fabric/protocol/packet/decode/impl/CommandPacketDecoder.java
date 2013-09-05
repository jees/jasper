package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendChatMessagePacketEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class CommandPacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		String commandString = packet.getRuneScapeString();
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		if (command.equals("position")) {
			String position = "Position: [X: " + player.getPosition().getX() + "] - [Y: " + player.getPosition().getY() + "]";
			player.writePacketMessage(new SendChatMessagePacketEncoder(position));
		}	
	}
}