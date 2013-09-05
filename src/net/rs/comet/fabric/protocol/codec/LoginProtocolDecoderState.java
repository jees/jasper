package net.rs.comet.fabric.protocol.codec;

public enum LoginProtocolDecoderState {
	
	/*
	 * A connection request has been received.
	 */
	CONNECTION_REQUEST,
	
	/*
	 * The connection keys are being exchanged.
	 */
	KEY_EXCHANGE,
	
	/*
	 * The connection is now relaying input and output data to determine if
	 * it's valid.
	 */
	CLIENT_HANDSHAKE,
}