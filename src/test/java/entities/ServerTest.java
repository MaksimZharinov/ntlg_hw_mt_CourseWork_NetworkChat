package entities;

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private String settingsPath = "src/main/resources/settings.txt";
    private String host = "127.0.0.1";
    private int port = 8888;
    private Server server;
    private TelnetClient telnetClient = new TelnetClient();

    @BeforeEach
    void setUp() {
        server = new Server();
    }

    @AfterEach
    void tearDown() {
        server.closeClient();
        server.closeServer();
    }

    @Test
    void getPort() {

        server.readPort(settingsPath);

        assertEquals(port, server.getPort());
    }

    @Test
    void getClient() {
        server.readPort(settingsPath);
        server.startServer();
        new Thread(() -> {
            server.connectClient();
        }).start();
        try {
            telnetClient.connect(InetAddress.getByName("localhost"), server.getPort());
        } catch (IOException e) {
            System.err.println(e);
        }
        assertTrue(telnetClient.isConnected());

        Socket result = server.getClient();
        try {
            telnetClient.disconnect();
        } catch (IOException e) {
            System.err.println(e);
        }

        assertTrue(result != null);
    }

    @Test
    void readPort() {

        assertFalse(server.readPort(""));
        assertFalse(server.readPort("test_false"));
        assertTrue(server.readPort(settingsPath));
    }

    @Test
    void startServer() {

        assertFalse(server.startServer());

        server.readPort(settingsPath);

        assertTrue(server.startServer());
    }

    @Test
    void connectClient() {

        AtomicBoolean result = new AtomicBoolean(false);

        assertFalse(server.connectClient());

        server.readPort(settingsPath);
        server.startServer();
        new Thread(() -> {
            result.set(server.connectClient());
        }).start();
        try {
            telnetClient.connect(InetAddress.getByName("localhost"), server.getPort());
            telnetClient.disconnect();
        } catch (IOException e) {
            System.err.println(e);
        }

        assertTrue(result.get());
    }

    @Test
    void sendToAll() {
        ConcurrentHashMap<String, PrintWriter> testMap = new ConcurrentHashMap<>();
        testMap.put("test_name1", new PrintWriter(new OutputStreamWriter(System.out)));
        testMap.put("test_name2", new PrintWriter(new OutputStreamWriter(System.out)));
        testMap.put("test_name3", new PrintWriter(new OutputStreamWriter(System.out)));
        String testMessage = "test_message";

        assertTrue(server.sendToAll(testMap, testMessage));
    }

    @Test
    void closeClient() {
        assertTrue(server.closeClient());
        server.readPort(settingsPath);
        server.startServer();
        new Thread(() -> {
            server.connectClient();
        }).start();
        try {
            telnetClient.connect(InetAddress.getByName("localhost"), server.getPort());
        } catch (IOException e) {
            System.err.println(e);
        }

        assertTrue(server.closeClient());
    }
}