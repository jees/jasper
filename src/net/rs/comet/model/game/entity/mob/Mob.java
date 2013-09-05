package net.rs.comet.model.game.entity.mob;

import net.rs.comet.model.game.entity.Entity;

public class Mob extends Entity {
	
	/**
	 * The identity index.
	 */
	private int identity;

	/**
	 * The default class constructor.
	 * 
	 * @param identiy The identity index.
	 */
	public Mob(int identity) {
		setIdentity(identity);
	}
	
	/**
	 * Returns the identity index.
	 * 
	 * @return The returned index.
	 */
	public int getIdentity() {
		return identity;
	}

	/**
	 * Modifies the identity index.
	 * 
	 * @param identity The new modification.
	 */
	public void setIdentity(int identity) {
		this.identity = identity;
	}
}