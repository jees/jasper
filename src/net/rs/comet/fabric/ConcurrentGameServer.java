package net.rs.comet.fabric;

public interface ConcurrentGameServer {

    /**
     * Binds the networking server to a fixed address.
     * 
     * @param port The fixed numerical address of the server.
     * 
     * @param address The IP or host address of the server,
     */
    void bindServer(int port, String address);
     
    /**
     * Processes the attributes related to the concurrent server.
     */
    void processServerAttributes();
}