package net.rs.comet.utilities;

public class ApplicationConstants {
    
    /**
     * The numerical address of the server.
     */
    public static final int SERVER_PORT = 43594;
    
    /**
     * The IP address of the server.
     */
    public static final String SERVER_ADDRESS = "localhost";
    
    /**
     * The protocol version of the server.
     */
    public static final int SERVER_REVISION = 317;

    /**
     * The slot of the player's chest.
     */
    public static final int APPEARANCE_SLOT_CHEST = 0;
    
    /**
     * The slot of the player's arm.
     */
    public static final int APPEARANCE_SLOT_ARMS = 1;
    
    /**
     * The slot of the player's legs.
     */
    public static final int APPEARANCE_SLOT_LEGS = 2;
    
    /**
     * The slot of the player's head.
     */
    public static final int APPEARANCE_SLOT_HEAD = 3;
    
    /**
     * The slot of the player's hands.
     */
    public static final int APPEARANCE_SLOT_HANDS = 4;
    
    /**
     * The slot of the player's feet.
     */
    public static final int APPEARANCE_SLOT_FEET = 5;
    
    /**
     * The slot of the player's beard.
     */
    public static final int APPEARANCE_SLOT_BEARD = 6;
    
    /**
     * The button clicked when a player requests a log-out.
     */
    public static final int LOG_OUT_BUTTON = 2458;
    
    /**
     * The numerical moderator rank.
     */
    public static final int MODERATOR = 1;
    
    /**
     * If numerical administrator rank.
     */
    public static final int ADMINISTRATOR = 2;
    
	/**
	 * The possible sizes of the incoming packets.
	 */
	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, 0, 2, 0, 6, 0, 12, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 8, 4, 0, 0, 2, 2, 6, 0, 6, 0, -1, 0, 0, 0, 0, 0, 0, 0, 12,
		0, 0, 0, 0, 8, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 2, 2, 8, 6,
		0, -1, 0, 6, 0, 0, 0, 0, 0, 1, 4, 6, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0,
		-1, 0, 0, 13, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0,
		0, 1, 0, 6, 0, 0, 0, -1, 0, 2, 6, 0, 4, 6, 8, 0, 6, 0, 0, 0, 2, 0,
		0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 1, 2, 0, 2, 6, 0, 0, 0, 0, 0, 0,
		0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 3, 0,
		2, 0, 0, 8, 1, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,
		0, 4, 0, 4, 0, 0, 0, 7, 8, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, -1, 0, 6,
		0, 1, 0, 0, 0, 6, 0, 6, 8, 1, 0, 0, 4, 0, 0, 0, 0, -1, 0, -1, 4, 0,
		0, 6, 6, 0, 0, 0 
	};
	
	/**
	 * A constant array to hold the player's who have a rank.
	 */
	public static final Object[][] PLAYER_RIGHTS = {
			/*
			 * The default name space for the MoparScape client.
			 */
			{"Mopar", ADMINISTRATOR}
	};
}