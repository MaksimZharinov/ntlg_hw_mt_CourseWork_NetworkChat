package entities;

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    public void setUp() {
        try {
            server = new Server(settings);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void isConnect() throws IOException {
        telnetClient = new TelnetClient();
        telnetClient.connect(host, port);

        assertTrue(telnetClient.isConnected());
    }

    @Test
    void connectClient() throws IOException {
        Client client = new Client(settings);
        Socket socket = null;

        socket = server.connectClient();

        assertTrue(socket != null);
    }

    @Test
    void getPort() {

        int result = server.getPort();

        assertEquals(port, result);
    }

}