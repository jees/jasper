package org.jasper.net.login;

/**
 * The login details given to a player upon login
 * @author Ares_
 *
 */
public final class LoginDetails {

	/**
	 * The players username
	 */
	private final String username;
	
	/**
	 * The players password
	 */
	private final String password;
	
	/**
	 * Constructs the {@link LoginDetails}
	 * @param username The players username
	 * @param password The players password
	 */
	public LoginDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the players username
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the players password
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}
	
}