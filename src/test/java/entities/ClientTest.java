package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {
    private String settingsPath = "src/main/resources/settings.txt";
    private String host = "127.0.0.1";
    private int port = 8888;
    private Server server;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }


    @Test
    void readPort() {

        assertFalse(client.readPort(""));
        assertFalse(client.readPort("test_false"));
        assertTrue(client.readPort(settingsPath));
    }

    @Test
    void connect() {

        assertFalse(client.connect(host));

        server = new Server();
        server.readPort(settingsPath);
        server.startServer();
        new Thread(() -> {
            server.connectClient();
        }).start();
        client.readPort(settingsPath);

        assertTrue(client.connect(host));

        client.closeClient();
        server.closeClient();
        server.closeServer();
    }

    @Test
    void send() {

        assertFalse(client.send("test_msg"));

        server = new Server();
        server.readPort(settingsPath);
        server.startServer();
        new Thread(() -> {
            server.connectClient();
        }).start();
        client.readPort(settingsPath);
        client.connect(host);

        assertTrue(client.send("test_msg"));

        client.closeClient();
        server.closeClient();
        server.closeServer();
    }
}