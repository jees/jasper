package net.rs.comet.model.game.item;

public class Item {
	
	/**
	 * The identity number of the item.
	 */
	private int itemIdentity;
	
	/**
	 * The amount of the item.
	 */
	private int itemAmount;
	
	/**
	 * The default class constructor for {@link Item}.
	 * 
	 * @param identity The identity of the item.
	 * 
	 * @param amount The amount of the item.
	 */
	public Item(int identity, int amount) {
		setItemIdentity(identity);
		setItemAmount(amount);
	}

	/**
	 * Returns the identity number of the item.
	 * 
	 * @return The returned number.
	 */
	public int getItemIdentity() {
		return itemIdentity;
	}

	/**
	 * Modifies the identity number of the item.
	 * 
	 * @param itemIdentity The new modification.
	 */
	public void setItemIdentity(int itemIdentity) {
		this.itemIdentity = itemIdentity;
	}

	/**
	 * Returns the amount of the item.
	 * 
	 * @return The returned amount.
	 */
	public int getItemAmount() {
		return itemAmount;
	}

	/**
	 * Modifies the amount of the item.
	 * 
	 * @param itemAmount The new modification.
	 */
	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
}