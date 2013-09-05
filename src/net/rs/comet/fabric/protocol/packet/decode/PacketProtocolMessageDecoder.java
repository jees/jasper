package net.rs.comet.fabric.protocol.packet.decode;

import net.rs.comet.fabric.protocol.packet.Packet;
import net.rs.comet.model.game.entity.player.Player;

public interface PacketProtocolMessageDecoder {

    /**
     * Decodes an incoming packet according to #317 protocol.
     * 
     * @param packet The packet being handler.
     * 
     * @param player The player of interest.
     */
    void decodePacket(Packet packet, Player player);
}