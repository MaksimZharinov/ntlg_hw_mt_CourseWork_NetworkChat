import com.sun.mail.util.LineInputStream;
import entities.Logger;
import entities.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static Server server;
    private static final String SETTINGS_PATH = "src/main/resources/settings.txt";
    private static final String LOG_PATH = "src/main/resources/server_log.txt";
    private static List<Thread> threads = new LinkedList<>();
    private static ConcurrentHashMap<String, DataOutputStream> clients = new ConcurrentHashMap<>();
    public static Logger logger;

    public static void main(String[] args) {
        try {
            logger = new Logger(LOG_PATH);
            server = new Server(SETTINGS_PATH);
            logger.setName("SERVER");
            logger.log("Start");
            while (true) {
                server.connectClient();
                logger.log("New client is trying to connect...");
                server.setName();
                logger.log(server.getName() + " has connected");
                clients.put(server.getName(), (DataOutputStream) server.getClient().getOutputStream());
                new Thread(() -> {
                    while(true) {

                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void send(String msg) {

    }


}
