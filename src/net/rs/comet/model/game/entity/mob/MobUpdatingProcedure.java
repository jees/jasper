package net.rs.comet.model.game.entity.mob;

import java.util.Iterator;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.player.Player;

public class MobUpdatingProcedure {
	
	/**
	 * Performs the prior to main update for the {@link Mob}.
	 * 
	 * @param mob The non-player character.
	 */
	public static void preUpdate(Mob mob) {
		mob.getWalkingQueue().updateNextMovement();
	}
	
	/**
	 * Performs the post to main update for the {@link Mob}.
	 * 
	 * @param mob The non-player character.
	 */
	public static void postUpdate(Mob mob) {
		mob.setUpdateRequired(false);
		mob.setAnimationUpdateRequired(false);
		mob.setGraphicUpdateRequired(false);
	}
	
	/**
	 * Updates the visual attributes of a non-player character.
	 * 
	 * @param player The player association.
	 * 
	 * @param mob The non-player character.
	 * 
	 * @param update The update dispatcher.
	 */
	public static void updateNonPlayerCharacterState(Player player, Mob mob, PacketBuilder update) {
		int mask = 0x0;
		update.writeByte((byte) mask);
	}
	
	/**
	 * Updates the movement of a non-player character.
	 * 
	 * @param packet The packet dispatcher.
	 * 
	 * @param mob The non-player character.
	 */
	public static void updateNonPlayerCharacterMovement(PacketBuilder packet, Mob mob) {
		if (mob.getWalkingDirection() == -1) {
			if (mob.isUpdateRequired()) {
				packet.writeBits(1, 1);
				packet.writeBits(2, 0);
			} else {
				packet.writeBits(1, 0);
			}
		} else {
			packet.writeBits(1, 1);
			packet.writeBits(2, 1);
			packet.writeBits(3, mob.getWalkingDirection());
			packet.writeBits(1, 1);
		}
	}
	
	/**
	 * Adds a new non-player character into the virtual world.
	 * 
	 * @param packet The packet dispatcher.
	 * 
	 * @param player The player association.
	 * 
	 * @param mob The non-player character.
	 */
	public static void addNewNonPlayerCharacter(PacketBuilder packet, Player player, Mob mob) {
		packet.writeBits(14, mob.getIndex());
		int yPosition = mob.getPosition().getY() - player.getPosition().getY();
		int xPosition = mob.getPosition().getX() - player.getPosition().getX();
		packet.writeBits(5, yPosition);
		packet.writeBits(5, xPosition);
		packet.writeBits(1, 0);
		packet.writeBits(12, mob.getIdentity());
		packet.writeBits(1, 1);
	}
	
	/**
	 * Updates a non-player character and the virtual world around them.
	 * 
	 * @param player The player association.
	 */
	public static void updateNonPlayerCharacter(Player player) {
		PacketBuilder packet = new PacketBuilder();
		PacketBuilder update = new PacketBuilder();
		packet.writeVariableShortPacketHeader(player.getEncoder(), 65);
		packet.startBitAccess();
		packet.writeBits(8, player.getLocalNpcs().size());
		for (Iterator<Mob> iterator = player.getLocalNpcs().iterator(); iterator.hasNext();) {
			Mob npc = iterator.next();
			if (npc.getPosition().isWithinDistance(player.getPosition())) {
				updateNonPlayerCharacterMovement(packet, npc);
				if (npc.isUpdateRequired()) {
					updateNonPlayerCharacterState(player, npc, update);
				}
			} else {
				iterator.remove();
				packet.writeBits(1, 1);
				packet.writeBits(2, 3);
			}
		}
		for (Mob npc : Game.getSingleton().getMobs().keySet()) {
			if (npc == null || player.getLocalNpcs().contains(npc)) {
				continue;
			}
			if (npc.getPosition().isWithinDistance(player.getPosition())) {
				player.getLocalNpcs().add(npc);
				addNewNonPlayerCharacter(packet, player, npc);
				if (npc.isUpdateRequired()) {
					updateNonPlayerCharacterState(player, npc, update);
				}
			}
		}
		if (!update.isEmpty()) {
			packet.writeBits(14, 16383);
			packet.startByteAccess();
			packet.writeDynamicBuffer(update.getBuffer());
		} else {
			packet.startByteAccess();
		}
		packet.finishVariableShortPacketHeader();
		player.getChannel().write(packet);
	}
}