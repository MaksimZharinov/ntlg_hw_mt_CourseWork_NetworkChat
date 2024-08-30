import org.apache.commons.net.telnet.TelnetClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private TelnetClient telnetClient;
    private Server server;
    private Client1 client;
    private String host = "127.0.0.1";
    private int port = 8888;

    @Test
    void isConnect() {
        //тестировал на поднятом сервере
        telnetClient = new TelnetClient();
        try {
            telnetClient.connect(host, port);
        } catch (IOException e){
            System.err.println(e);
        }

        boolean result = telnetClient.isConnected();

        assertTrue(result);

        try {
            telnetClient.disconnect();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void sendMessToAll() {
        Map<Integer, User> users = new HashMap<>();
        users.put(1111, new User(new Socket(), new PrintWriter(System.out)));
        users.put(2222, new User(new Socket(), new PrintWriter(System.out)));
        users.put(3333, new User(new Socket(), new PrintWriter(System.out)));

        server = new Server();

        server.sendMessToAll("test_mess");
    }

    @Test
    void waitMessAndSend() {
    }
}