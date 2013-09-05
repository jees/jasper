package net.rs.comet.model.game.entity.player;

import java.util.LinkedList;
import java.util.List;

import net.rs.comet.fabric.protocol.packet.encode.PacketProtocolMessageEncoder;
import net.rs.comet.fabric.protocol.packet.security.ISAACCipher;
import net.rs.comet.model.game.Position;
import net.rs.comet.model.game.entity.Entity;
import net.rs.comet.model.game.entity.mob.Mob;
import net.rs.comet.model.game.entity.player.action.PlayerAction;
import net.rs.comet.model.game.item.Item;
import net.rs.comet.utilities.ApplicationConstants;

import org.jboss.netty.channel.Channel;

public class Player extends Entity {

	/**
	 * The name of the player's account.
	 */
	private String accountName;

	/**
	 * The password of the player's account.
	 */
	private String accountPassword;
	
	/**
	 * The inference of the updating procedure.
	 */
	private PlayerUpdatingProcedure update = new PlayerUpdatingProcedure(this);
	
	/**
	 * The gender of the player.
	 */
	private int gender = 0;

	/**
	 * The connection key of the player.
	 */
	private int clientConnectionKey;
	
	/**
	 * The items in a players inventory.
	 */
	private Item[] inventory = new Item[28];
	
	/**
	 * The items the player has equipped.
	 */
	private int[] equipment = new int[12];
	
	/**
	 * The privileges of the player.
	 */
	private int playerRights = 0;
	
	/**
	 * How much energy the player currently has.
	 */
	private int energyLeft = 100;
	
	/**
	 * If the player has completed the login procedure.
	 */
	private boolean loggedIn;

	/**
	 * Holds the player's appearance values.
	 */
	private int[] appearanceValues = new int[13];

	/**
	 * Holds the color indexes of the appearance.
	 */
	private int[] colorValues = new int[5];

	/**
	 * The channel a player is connection through.
	 */
	private Channel channel;

	/**
	 * The chat text.
	 */
	private byte chatText[] = new byte[256];
	
	/**
	 * The list of local players.
	 */
	private final List<Player> localPlayers = new LinkedList<Player>();

	/**
	 * The list of local NPCs.
	 */
	private final List<Mob> localNpcs = new LinkedList<Mob>();
	
	/**
	 * The chat packet modifiers.
	 */
	private int chatTextEffects = 0, chatTextColor = 0;

	/**
	 * The packet encoder and decoder.
	 */
	private ISAACCipher decoder, encoder;

	/**
	 * The index of the player.
	 */
	private int index;

	/**
	 * Retrieves the name of the player's account.
	 * 
	 * @return The name that was retrieved,
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Retrieves the password of the player's account.
	 * 
	 * @return The password that was retrieved.
	 */
	public String getAccountPassword() {
		return accountPassword;
	}

	/**
	 * Retrieves the channel of activity.
	 * 
	 * @return The retrieved channel.
	 */
	public Channel getChannel() {
		return channel;
	}
	
	/**
	 * Retrieves the local players list.
	 * 
	 * @return The retrieved list.
	 */
	public List<Player> getLocalPlayers() {
		return localPlayers;
	}

	/**
	 * Retrieves the connection key of the player's account.
	 * 
	 * @return The connection key that was retrieved.
	 */
	public int getClientConnectionKey() {
		return clientConnectionKey;
	}

	/**
	 * Retrieves the packet decoder.
	 * 
	 * @return The decoder that was retrieved.
	 */
	public ISAACCipher getDecoder() {
		return decoder;
	}

	/**
	 * Retrieves the packet encoder.
	 * 
	 * @return The encoder that was retrieved.
	 */
	public ISAACCipher getEncoder() {
		return encoder;
	}

	/**
	 * Retrieves the index of the player.
	 * 
	 * @return The retrieved index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Modifies the name of the player's account.
	 * 
	 * @param accountName The new modification.
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Modifies the password of the player's account.
	 * 
	 * @param accountPassword The new modification.
	 */
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	/**
	 * Modifies the channel of activity.
	 * 
	 * @param channel The new modification.
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	/**
	 * Modifies the connection key of the player's account.
	 * 
	 * @param clientConnectionKey The new modification.
	 */
	public void setClientConnectionKey(int clientConnectionKey) {
		this.clientConnectionKey = clientConnectionKey;
	}

	/**
	 * Modifies the packet decoder.
	 * 
	 * @param decoder The new modification.
	 */
	public void setDecoder(ISAACCipher decoder) {
		this.decoder = decoder;
	}

	/**
	 * Modifies the packet encoder.
	 * 
	 * @param encoder The new modification.
	 */
	public void setEncoder(ISAACCipher encoder) {
		this.encoder = encoder;
	}

	/**
	 * Modifies the index of the player.
	 * 
	 * @param index The new modification.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Retrieves the appearance values of the player.
	 * 
	 * @return The retrieved values.
	 */
	public int[] getAppearanceValues() {
		return appearanceValues;
	}

	/**
	 * Modifies the appearance values of the player.
	 * 
	 * @param appearanceValues The new modification.
	 */
	public void setAppearanceValues(int[] appearanceValues) {
		this.appearanceValues = appearanceValues;
	}

	/**
	 * Retrieves the color indexes for the player.
	 * 
	 * @return The retrieved indexes.
	 */
	public int[] getColorValues() {
		return colorValues;
	}

	/**
	 * Modifies the color indexes of the player.
	 * 
	 * @param colorValues The new modification.
	 */
	public void setColorValues(int[] colorValues) {
		this.colorValues = colorValues;
	}

	/**
	 * Returns the chat text.
	 * 
	 * @return The returned text.
	 */
	public byte[] getChatText() {
		return chatText;
	}

	/**
	 * Modifies the chat text.
	 * 
	 * @param chatText The new modification.
	 */
	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}

	/**
	 * Returns the chat effects.
	 * 
	 * @return The returned effects.
	 */
	public int getChatTextEffects() {
		return chatTextEffects;
	}

	/**
	 * Modifies the chat effects.
	 * 
	 * @param chatTextEffects The new modification.
	 */
	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}

	/**
	 * Returns the chat text color.
	 * 
	 * @return The returned color.
	 */
	public int getChatTextColor() {
		return chatTextColor;
	}

	/**
	 * Modifies the chat text color.
	 * 
	 * @param chatTextColor The new modification.
	 */
	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}

	/**
	 * Writes an outgoing packet transmission.
	 * 
	 * @param message The packet to be written.
	 */
	public void writePacketMessage(PacketProtocolMessageEncoder message) {
		message.encodePacket(this);
	}
	
	/**
	 * Performs an action in fixed circumstances.
	 * 
	 * @param action The action to be performed.
	 */
	public void performAction(PlayerAction action) {
		action.performAction(this);
	}
	
	/**
	 * Checks if a player is logged in.
	 * 
	 * @return The result of the check.
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Modifies if a player is logged in.
	 * 
	 * @param loggedIn The new modification.
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	/**
	 * Returns the energy the player has left.
	 * 
	 * @return The returned value.
	 */
	public int getEnergyLeft() {
		return energyLeft;
	}

	/**
	 * Modifies the energy the player has left.
	 * 
	 * @param energyLeft The returned value.
	 */
	public void setEnergyLeft(int energyLeft) {
		this.energyLeft = energyLeft;
	}
	
	/**
	 * Returns the rights of the player.
	 * 
	 * @return The returned rights.
	 */
	public int getRights() {
		return playerRights;
	}

	/**
	 * Modifies the rights of the player.
	 * 
	 * @param playerRights The new modification.
	 */
	public void setRights(int playerRights) {
		this.playerRights = playerRights;
	}
	
	/**
	 * Returns the items in the players inventory.
	 * 
	 * @return The returned items.
	 */
	public Item[] getInventory() {
		return inventory;
	}

	/**
	 * Modifies the items in a players inventory.
	 * 
	 * @param invetory The new modification.
	 */
	public void setInventory(Item[] invetory) {
		this.inventory = invetory;
	}

	/**
	 * Returns the items the player has equipped.
	 * 
	 * @return  The returned items.
	 */
	public int[] getEquipment() {
		return equipment;
	}

	/**
	 * Modifies the items the player has equipped.
	 * 
	 * @param equipment The new modification.
	 */
	public void setEquipment(int[] equipment) {
		this.equipment = equipment;
	}
	
	/**
	 * Returns the gender of the player.
	 * 
	 * @return The returned gender.
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * Modifies the gender of the player.
	 * 
	 * @param gender The new modification.
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	/**
	 * Returns an inference of the updating procedure.
	 * 
	 * @return The returned inference.
	 */
	public PlayerUpdatingProcedure getUpdate() {
		return update;
	}
	
	/**
	 * Returns a compaction of the NPCs in the players region.
	 * 
	 * @return The returned compaction.
	 */
	public List<Mob> getLocalNpcs() {
		return localNpcs;
	}
	
	/**
	 * Sets the players default appearance.
	 */
	public void setDefaultAppearance() {
		getAppearanceValues()[0] = 22;
		getAppearanceValues()[1] = 26;
		getAppearanceValues()[2] = 36;
		getAppearanceValues()[3] = 6;
		getAppearanceValues()[4] = 33;
		getAppearanceValues()[5] = 42;
		getAppearanceValues()[6] = 14;
		getColorValues()[0] = 6;
		getColorValues()[1] = 7;
		getColorValues()[2] = 7;
		getColorValues()[3] = 4;
		getColorValues()[4] = 8;
	}

	/**
	 * Represents a player in the virtual world.
	 * 
	 * @param name The name of the player's account.
	 * 
	 * @param password The password of the player's account.
	 * 
	 * @param key The connection key of the player's account.
	 * 
	 * @param channel The channel of activity.
	 */
	public Player(String name, String password, int key, Channel channel) {
		setPosition(new Position(3222, 3218, 0));
		setAccountName(name);
		setAccountPassword(password);
		setClientConnectionKey(key);
		setChannel(channel);
		setDefaultAppearance();
	}
	
	/**
	 * Returns the privilege of the player being observed.
	 * 
	 * @param player The player who's privilege is being retrieved.
	 * 
	 * @return The result of the observation operation.
	 */
	public int determinePlayerRights(Player player) {
		for (Object[] rights : ApplicationConstants.PLAYER_RIGHTS) {
			if (player.getAccountName().equalsIgnoreCase((String) rights[0])) {
				player.setRights((int) rights[1]);
			}
		}
		return player.getRights();
	}
}