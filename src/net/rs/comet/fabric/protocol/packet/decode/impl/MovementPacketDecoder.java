package net.rs.comet.fabric.protocol.packet.decode.impl;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.fabric.protocol.packet.decode.PacketProtocolMessageDecoder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendCloseInterfacePacketEncoder;
import net.rs.comet.model.game.entity.player.Player;

public class MovementPacketDecoder implements PacketProtocolMessageDecoder {

	@Override
	public void decodePacket(Packet packet, Player player) {
		/*
		 * Closes any open interfaces before allowing the player to move.
		 */
		player.writePacketMessage(new SendCloseInterfacePacketEncoder());
		/*
		 * Re-defines the walking queue to begin the new procedure.
		 */
		player.getWalkingQueue().reset();
		/*
		 * The size of the packet defined by its length.
		 */
		int packetSize = packet.getLength();
		/*
		 * The packet size can't hold a value higher than 240 and will be modified 
		 * if it does.
		 */
		if(packet.getOpcode() == 248) {
		    packetSize -= 14;
		}
		/*
		 * Calculates the steps taken based on the defined length.
		 */
		final int stepsTaken = (packetSize - 5) / 2;
		/*
		 * Calculates the route taken.
		 */
		final int[][] route = new int[stepsTaken][2];
		/*
		 * Reads the initial X position of the step.
		 */
		final int firstX = packet.readLittleEndianShortAddition();
		/*
		 * Reads the route specified by the client.
		 */
		for (int i = 0; i < stepsTaken; i++) {
		    route[i][0] = packet.getBuffer().readByte();
		    route[i][1] = packet.getBuffer().readByte();
		}
		/*
		 * Reads the initial Y position of the step.
		 */
		final int firstY = packet.readLittleEndianShort();
		/*
		 * Determines if the route was ran.
		 */
		final boolean stepsRan = packet.readNegatedByte() == 1;
		/*
		 * Configures the run queue accordingly.
		 */
		player.getWalkingQueue().setRunQueue(stepsRan);
		/*
		 * Adds the steps taken into the movement queue.
		 */
		player.getWalkingQueue().addExternalStep(firstX, firstY );
		/*
		 * Adds the route taken into the movement queue.
		 */
		for (int i = 0; i < stepsTaken; i++) {
		    route[i][0] += firstX;
		    route[i][1] += firstY;
		    player.getWalkingQueue().addExternalStep(route[i][0], route[i][1]);
		}
		/*
		 * Defines the movement queue once the procedure is finished.
		 */
		player.getWalkingQueue().finish();
	}
}