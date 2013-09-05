package net.rs.comet.fabric.protocol.codec;

import java.security.SecureRandom;

import net.rs.comet.fabric.protocol.packet.PacketBuilder;
import net.rs.comet.fabric.protocol.packet.encode.impl.SendInitialPacketEncoder;
import net.rs.comet.fabric.protocol.packet.security.ISAACCipher;
import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.entity.player.Player;
import net.rs.comet.utilities.ApplicationConstants;
import net.rs.comet.utilities.ApplicationUtilities;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public class LoginProtocolDecoder extends ReplayingDecoder<LoginProtocolDecoderState> {

	/**
	 * The default class constructor for {@link LoginProtocolDecoder}.
	 */
	public LoginProtocolDecoder() {
		/*
		 * When a new connection is received it is set to this procedure state
		 * by default.
		 */
		super(LoginProtocolDecoderState.CONNECTION_REQUEST);
	}  

	@Override
	protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer, LoginProtocolDecoderState state) throws Exception { 
		switch (state) {

		/*
		 * A connection request has been received and the initial packet
		 * needs to be decoded and determined to have a valid index.
		 */
		case CONNECTION_REQUEST:
			/*
			 * Checks if the initial opcode has the correct value of 14.
			 */
			if (buffer.readUnsignedByte() != 14) {
				channel.close();
				return false;
			} else {
				/*
				 * Advance to the next login stage.
				 */
				setState(LoginProtocolDecoderState.KEY_EXCHANGE);
				return true;
			}

		/*
		 * The key exchange, This is  where the server writes it's session key to the 
		 * client. The entire session is composed of an allocation of 17, two longs and a byte.
		 */
		case KEY_EXCHANGE:
			/*
			 * If there isn't enough readable data in the internal buffer the procedure
			 * can't be completed.
			 */
			if (buffer.readableBytes() < 1) {
				return false;
			}
			/*
			 * The login server which is read prior to the key exchange.
			 */
			buffer.readUnsignedByte();
			/*
			 * Allocates a new message dispatcher. 
			 */
			PacketBuilder response = new PacketBuilder();
			response.writeLong(0);
			response.writeByte((byte) 0);
			response.writeLong(new SecureRandom().nextLong());
			/*
			 * Writes the final product to the client for consideration.
			 */
			channel.write(response);
			/*
			 * Advance to the next login state.
			 */
			setState(LoginProtocolDecoderState.CLIENT_HANDSHAKE);
			return true;

		/*
		 * The client & server perform a multiple step handshake to determine if
		 * the connection is valid and what data it possess. 
		 */
		case CLIENT_HANDSHAKE:
			/*
			 * If there isn't enough readable data in the internal buffer the procedure
			 * can't be completed.
			 */
			if (buffer.readableBytes() < 2) {
				return false;
			}
			/*
			 * The prior to login opcode. A value of 18 means it's a re-connection,
			 * while a value of 16 means it's a new connection.
			 */
			int loginResponseCode = buffer.readUnsignedByte();
			/*
			 * Checks to see if the login opcode has a valid index.
			 */
			if (loginResponseCode != 16 && loginResponseCode != 18) {
				channel.close();
				return false;
			}
			/*
			 * The size of the login block which will be encrypted.
			 */
			int loginBlockSize = buffer.readByte();
			/*
			 * The algorithm used to encrypt the login block size.
			 */
			int encryptedLoginBlockSize = loginBlockSize - (36 + 1 + 1 + 2);
			/*
			 * If the newly encrypted block doesn't have the correct size the procedure
			 * can't be completed.
			 */
			if (encryptedLoginBlockSize < 1) {
				channel.close();
				return false;
			}
			/*
			 * If the clients block doesn't have the correct size the procedure 
			 * can't be completed.
			 */
			if (buffer.readableBytes() < loginBlockSize) {
				channel.close();
				return false;
			}
			/*
			 * Checks to see if the magic number sent by the client is correct.
			 */
			if (buffer.readUnsignedByte() != 0xFF) {
				channel.close();
				return false;
			}
			/*
			 * Checks to see if the clients protocol revision is the same as ours.
			 */
			if (buffer.readShort() != ApplicationConstants.SERVER_REVISION) {
				channel.close();
				return false;
			}
			/*
			 * Reads the memory version of the client.
			 */
			buffer.readByte();
			/*
			 * Skip over the decoding of the RSA encryption block.
			 */
			for (int i = 0; i < 9; i ++) {
				buffer.readInt();
			}
			/*
			 * Reads the length of the RSA encryption block.
			 */
			buffer.readUnsignedByte();
			/*
			 * Validates that the RSA encryption block was decoded properly.
			 */
			if (buffer.readUnsignedByte() != 10) {
				channel.close();
				return false;
			}
			/*
			 * Reads the client half of the encryption.
			 */
			long clientHalf = buffer.readLong();
			/*
			 * Reads the server half of the encryption. 
			 */
			long serverHalf = buffer.readLong();
			/*
			 * The algorithm for generating the decoded value of the seed.
			 */
			int[] isaacSeed = { 
					(int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf 
			};
			/*
			 * Constructs the packet decoder.
			 */
			ISAACCipher decoder = new ISAACCipher(isaacSeed);
			/*
			 * The algorithm for generating the encoded value of the seed.
			 */
			for (int i = 0; i < isaacSeed.length; i++) {
				isaacSeed[i] += 50;
			}
			/*
			 * Constructs the packet encoder.
			 */
			ISAACCipher encoder = new ISAACCipher(isaacSeed);
			/*
			 * Reads the unique connection key of the player.
			 */
			int clientConnectionKey = buffer.readInt();
			/*
			 * Reads the name of the player's account.
			 */
			String accountName = ApplicationUtilities.readRuneScapeString(buffer);
			/*
			 * Reads the password of the player's account.
			 */
			String accountPassword = ApplicationUtilities.readRuneScapeString(buffer);
			/*
			 * Checks if the name and password are of valid length.
			 */
			if (accountName.length() > 12 && accountPassword.length() > 20) {
				channel.close();
				return false;
			}
			/*
			 * Checks if the name and password are not empty.
			 */
			if (accountName.isEmpty() || accountPassword.isEmpty()) {
				channel.close();
				return false;
			}
			/*
			 * Constructs the new {@link Player} with the recently read attributes.
			 */
			Player player = new Player(accountName, accountPassword, clientConnectionKey, channel);
			/*
			 * The return code.
			 */
			int returnCode = 2;
			/*
			 * Allocates a new dynamic packet structure. 
			 */
			PacketBuilder handshakeResponses = new PacketBuilder();
			/*
			 * The successful response code.
			 */
			handshakeResponses.writeByte((byte) returnCode);
			/*
			 * The rights of the player.
			 */
			handshakeResponses.writeByte((byte) player.determinePlayerRights(player));
			/*
			 * If the account of the player is flagged.
			 */
			handshakeResponses.writeByte((byte) 0);
			/*
			 * Writes the final responses to let the client know if the attempt was
			 * successful.
			 */
			channel.write(handshakeResponses);
			/*
			 * Sets the encoder used for encoding outgoing packets.
			 */
			player.setEncoder(encoder);
			/*
			 * Sets the decoder used for decoding outgoing packets.
			 */
			player.setDecoder(decoder);
			/*
			 * The player has successfully completed the login procedure.
			 */
			player.setLoggedIn(true);
			/*
			 * Registers the player into the virtual world for updating.
			 */
			Game.getSingleton().registerPlayer(player);
			System.out.println("The player [" + player.getAccountName() + "] has been registered. There are now [" + Game.getSingleton().getPlayers().size() + "] players online.");
			/*
			 * Writes the initial login packet.
			 */
			player.writePacketMessage(new SendInitialPacketEncoder());
			/*
			 * The login procedure has been successfully completed.
			 */
			context.getPipeline().replace("decoder", "packet-decoder", new PacketProtocolDecoder(player));
			return true;
		}
		return false;
	}
}