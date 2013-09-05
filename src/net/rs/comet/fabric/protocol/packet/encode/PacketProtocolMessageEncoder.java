package net.rs.comet.fabric.protocol.packet.encode;

import net.rs.comet.model.game.entity.player.Player;

public interface PacketProtocolMessageEncoder {
    
    /**
     * Encodes an outgoing packet according to #317 protocol.
     * 
     * @param player The player of interest.
     */
    void encodePacket(Player player);
}