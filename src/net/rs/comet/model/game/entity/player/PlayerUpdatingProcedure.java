package net.rs.comet.model.game.entity.player;

import java.util.Iterator;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendRegionalUpdatePacketEncoder;
import net.rs.comet.model.game.Game;
import net.rs.comet.utilities.ApplicationUtilities;

public class PlayerUpdatingProcedure  {
	
	/**
	 * The connected player.
	 */
	private Player master;
	
	/**
	 * The default class constructor for {@link PlayerUpdatingProcedure}.
	 * 
	 * @param master The connected player.
	 */
	public PlayerUpdatingProcedure(Player master) {
		this.setMaster(master);
	}
	
	/**
	 * Returns the connected player.
	 * 
	 * @return The returned player.
	 */
	public Player getMaster() {
		return master;
	}

	/**
	 * Modifies the connected player.
	 * 
	 * @param master The new modification.
	 */
	public void setMaster(Player master) {
		this.master = master;
	}

	/**
	 * Performs the concurrent updating procedure.
	 */
	public void performUpdate() {
		if (getMaster().isMapRegionChanging()) {
			getMaster().writePacketMessage(new SendRegionalUpdatePacketEncoder());
		}
		PacketBuilder builder = new PacketBuilder();
		PacketBuilder updateBlock = new PacketBuilder();
		builder.writeVariableShortPacketHeader(getMaster().getEncoder(), 81);
		builder.startBitAccess();
		updateThisPlayerMovement(builder);
		if (getMaster().isUpdateRequired()) {
			updatePlayerState(getMaster(), updateBlock, false);
		}
		builder.writeBits(8, getMaster().getLocalPlayers().size());
		for(Iterator<Player> it$ = getMaster().getLocalPlayers().iterator(); it$.hasNext();) {
			Player otherPlayer = it$.next();
			if (Game.getSingleton().getPlayers().containsKey(otherPlayer) && !otherPlayer.isHasTeleported()  && otherPlayer.getPosition().isWithinDistance(master.getPosition())) {
				updateOtherPlayerMovement(otherPlayer, builder);
				if (otherPlayer.isUpdateRequired()) {
					updatePlayerState(otherPlayer, updateBlock, false);
				}
			} else {
				it$.remove();
				builder.writeBits(1, 1);
				builder.writeBits(2, 3);
			}
		}
		for (Player otherPlayer : Game.getSingleton().getPlayers().keySet()) {
			if (getMaster().getLocalPlayers().size() >= 255) {
				break;
			}
			if(otherPlayer == getMaster() || getMaster().getLocalPlayers().contains(otherPlayer)) {
				continue;
			}
			if (otherPlayer.getPosition().isWithinDistance(getMaster().getPosition())) {
				getMaster().getLocalPlayers().add(otherPlayer);
				addNewPlayer(otherPlayer, builder);
				updatePlayerState(otherPlayer, updateBlock, true);
			}
		}
		if (!updateBlock.isEmpty()) {
			builder.writeBits(11, 2047);
			builder.startByteAccess();
			builder.writeDynamicBuffer(updateBlock.getBuffer());
		} else {
			builder.startByteAccess();
		}
		builder.finishVariableShortPacketHeader();
		master.getChannel().write(builder);
	}

	/**
	 * Performs the update executed after the main update.
	 */
	public void performPostUpdate() {
		master.setHasTeleported(false);
		master.setAppearanceUpdateRequired(false);
		master.setChatUpdateRequired(false);
		master.setMapRegionChanging(false);
		master.setAnimationUpdateRequired(false);
		master.setGraphicUpdateRequired(false);
		master.setUpdateRequired(false);
	}

	/**
	 * Performs the update executed prior to the main update.
	 */
	public void performPreUpdate() {
		master.getWalkingQueue().updateNextMovement();
	}

	/**
	 * Updates the movement of the other players in the virtual world.
	 * 
	 * @param player The other players.
	 * 
	 * @param builder The message dispatcher.
	 */
	public void updateOtherPlayerMovement(Player player, PacketBuilder builder) {
		if (player.getWalkingDirection() == -1) {
			if (player.isUpdateRequired()) {
				builder.writeBits(1, 1);
				builder.writeBits(2, 0);
			} else {
				builder.writeBits(1, 0);
			}
		} else if (player.getRunningDirection() == -1) {
			builder.writeBits(1, 1);
			builder.writeBits(2, 1);
			builder.writeBits(3, player.getWalkingDirection());
			builder.writeBits(1, player.isUpdateRequired() ? 1 : 0);
		} else {
			builder.writeBits(1, 1);
			builder.writeBits(2, 2);
			builder.writeBits(3, player.getWalkingDirection());
			builder.writeBits(3, player.getRunningDirection());
			builder.writeBits(1, player.isUpdateRequired() ? 1 : 0);
		}
	}

	/**
	 * Updates the movement of the connected player.
	 * 
	 * @param builder The message dispatcher.
	 */
	public void updateThisPlayerMovement(PacketBuilder builder) {
		if (master.isHasTeleported() || master.isMapRegionChanging()) {
			builder.writeBits(1, 1);
			builder.writeBits(2, 3);
			builder.writeBits(2, master.getPosition().getZ());
			builder.writeBits(1, 1);
			builder.writeBits(1, master.isUpdateRequired() ? 1 : 0);
			builder.writeBits(7, master.getPosition().getLocalY(master.getLastKnownPosition()));
			builder.writeBits(7, master.getPosition().getLocalX(master.getLastKnownPosition()));
		} else {
			if (master.getWalkingDirection() == -1) {
				if (master.isUpdateRequired()) {
					builder.writeBits(1, 1);
					builder.writeBits(2, 0);
				} else {
					builder.writeBits(1, 0);
				}
			} else {
				if (master.getRunningDirection() == -1) {
					builder.writeBits(1, 1);
					builder.writeBits(2, 1);
					builder.writeBits(3, master.getWalkingDirection());
					builder.writeBits(1, master.isUpdateRequired() ? 1 : 0);
				} else {
					builder.writeBits(1, 1);
					builder.writeBits(2, 2);
					builder.writeBits(3, master.getWalkingDirection());
					builder.writeBits(3, master.getRunningDirection());
					builder.writeBits(1, master.isUpdateRequired() ? 1 : 0);
				}
			}
		}
	}

	/**
	 * Updates the state and attributes of the players in the virtual world.
	 * 
	 * @param player The players.
	 * 
	 * @param update The message dispatcher.
	 * 
	 * @param forced If the update is considered to be forced.
	 */
	public void updatePlayerState(Player player, PacketBuilder update, boolean forced) {
		int mask = 0x0;
		if (player.isGraphicUpdateRequired()) {
			mask |= 0x100;
		}
		if (player.isAnimationUpdateRequired()) {
			mask |= 0x8;
		}
		if (player.isChatUpdateRequired() && player != master) {
			mask |= 0x80;
		}
		if (player.isAppearanceUpdateRequired() || forced) {
			mask |= 0x10;
		}
		if (mask >= 0x100) {
			mask |= 0x40;
			update.writeByte((byte) (mask & 0xFF));
			update.writeByte((byte) (mask >> 8));
		} else {
			update.writeByte((byte) mask);
		}
		if (player.isGraphicUpdateRequired()) {
			update.writeLittleEndianShort(player.getGraphicIndex());
			update.writeInteger(player.getGraphicDelay());
		}
		if (player.isAnimationUpdateRequired()) {
			update.writeLittleEndianShort(player.getAnimationIndex());
			update.writeNegatedByte((byte) player.getAnimationDelay());
		}
		if (player.isChatUpdateRequired() && player != master) {
			appendChat(update, player);
		}
		if (player.isAppearanceUpdateRequired() || forced) {
			appendAppearance(player, update);
		}
	}

	/**
	 * Adds a new player into the virtual world.
	 * 
	 * @param other The new player.
	 * 
	 * @param builder The message dispatcher.
	 */
	public void addNewPlayer(Player other, PacketBuilder builder) {
		builder.writeBits(11, other.getIndex());
		builder.writeBits(1, 1);
		builder.writeBits(1, 1);
		int yPosition = other.getPosition().getY() - master.getPosition().getY();
		int xPosition = other.getPosition().getX() - master.getPosition().getX();
		builder.writeBits(5, yPosition);
		builder.writeBits(5, xPosition);
	}

	/**
	 * Appends the chat properties of the players in the world.
	 * 
	 * @param properties The message dispatcher.
	 * 
	 * @param player The players.
	 */
	public void appendChat(PacketBuilder properties, Player player) {
		properties.writeLittleEndianShort(((player.getChatTextColor() & 0xFF) << 8) + (player.getChatTextEffects() & 0xFF));
		properties.writeByte((byte) player.getRights());
		properties.writeNegatedByte((byte) player.getChatText().length);
		properties.write(player.getChatText());
	}

	/**
	 * Appends the update properties of the players in the world.
	 * 
	 * @param player The players in the world.
	 * 
	 * @param builder The message dispatcher.
	 */
	public void appendAppearance(Player player, PacketBuilder builder) {
		PacketBuilder properties = new PacketBuilder();
		properties.getBuffer().writeByte(player.getGender()); 
		properties.getBuffer().writeByte(0);
		properties.getBuffer().writeByte(0);
		properties.getBuffer().writeByte(0);
		properties.getBuffer().writeByte(0);
		if (player.getEquipment()[3] != -1) {
			properties.writeShort((short) (0x200 + player.getEquipment()[3]));
		} else {
			properties.writeByte((byte) 0);
		}
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[0]));
		properties.getBuffer().writeByte(0);
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[1]));
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[2]));
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[3]));
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[4]));
		properties.writeShort((short) (0x100 + player.getAppearanceValues()[5]));
		if (player.getGender() == 0) {
			properties.writeShort((short) (0x100 + player.getAppearanceValues()[6]));
		} else {
			properties.writeByte((byte) 0);
		}
		properties.writeByte((byte) player.getColorValues()[0]);
		properties.writeByte((byte) player.getColorValues()[1]);
		properties.writeByte((byte) player.getColorValues()[2]);
		properties.writeByte((byte) player.getColorValues()[3]);
		properties.writeByte((byte) player.getColorValues()[4]);
		properties.getBuffer().writeShort(0x328); 
		properties.getBuffer().writeShort(0x337); 
		properties.getBuffer().writeShort(0x333); 
		properties.getBuffer().writeShort(0x334); 
		properties.getBuffer().writeShort(0x335); 
		properties.getBuffer().writeShort(0x336); 
		properties.getBuffer().writeShort(0x338);
		properties.getBuffer().writeLong(ApplicationUtilities.convertNameToLong(player.getAccountName()));
		properties.getBuffer().writeByte(126);
		properties.getBuffer().writeShort(10);
		builder.writeNegatedByte((byte) properties.getBuffer().writerIndex());
		builder.writeDynamicBuffer(properties.getBuffer());
	}
}