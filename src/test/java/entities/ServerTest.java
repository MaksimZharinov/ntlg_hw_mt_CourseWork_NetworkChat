package entities;

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private static int port = 8888;
    private static String host = "localhost";
    private static String settings = "src/main/resources/settings.txt";
    private TelnetClient telnetClient;
    private Server server;

    @BeforeEach
    void setUp() {
        try {
            server = new Server(settings);
            telnetClient = new TelnetClient();
            telnetClient.connect(host, port);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            telnetClient.disconnect();
            server.closeServer();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void isConnectTest() {

        assertTrue(telnetClient.isConnected());
    }

    @Test
    void connectClientTest() {

        assertTrue(server.connectClient());
    }

    @Test
    void getPortTest() {

        int result = server.getPort();

        assertEquals(port, result);
    }

    @Test
    void getClientTest() {

        server.connectClient();
        Socket result = server.getClient();

        assertNotNull(result);
    }

    @Test
    void closeClientTest() {

        server.connectClient();
        Socket testClient = server.getClient();
        boolean result = server.closeClient(testClient);

        assertTrue(result);
    }

    @Test
    void setNameTest() {

    }

    @Test
    void getNameTest() {

    }
}