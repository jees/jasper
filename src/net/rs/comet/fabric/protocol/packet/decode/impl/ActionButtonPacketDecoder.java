package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendLogOutPacketEncoder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendCloseInterfacePacketEncoder;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.model.game.entity.player.action.impl.TeleportingAction;
import net.rs.comet.utilities.ApplicationConstants;

public class ActionButtonPacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		int actionButtonId = packet.getBuffer().readShort();
		if (player.getRights() == 2) {
			System.out.println("Activated The Action Button [" + actionButtonId + "].");
		}
		switch (actionButtonId) {

		case ApplicationConstants.LOG_OUT_BUTTON:
			player.writePacketMessage(new SendLogOutPacketEncoder());
			player.getChannel().close();
			player.getChannel().disconnect();
			break;
			
		case 3651:
			player.setAppearanceUpdateRequired(true);
			player.writePacketMessage(new SendCloseInterfacePacketEncoder());
			break;
			
		case 1164:
			player.performAction(new TeleportingAction(3210, 3424, 0));
			break;
			
		case 1167:
			player.performAction(new TeleportingAction(3222, 3218, 0));
			break;
			
		case 153:
			player.getWalkingQueue().setIsRunning(true);
			player.getWalkingQueue().setRunQueue(true);
			break;
			
		case 152:
			player.getWalkingQueue().setIsRunning(false);
			player.getWalkingQueue().setRunQueue(false);
			break;
		}
	}
}