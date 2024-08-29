import entities.Logger;
import entities.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static final String EXIT = "/exit";
    private static final String SETTINGS_FILE = "src/main/resources/settings.txt";
    private static final String LOG_FILE = "src/main/resources/log_server.txt";
    private static Server server = new Server();
    private static Socket client = null;
    private static ConcurrentHashMap<String, PrintWriter> users = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {

        Logger serverLogger = new Logger(LOG_FILE);
        server.readPort(SETTINGS_FILE);
        serverLogger.setName("SERVER");
        serverLogger.log("START");
        server.startServer();

        while (true) {
            server.connectClient();
            Logger clientLogger = new Logger(LOG_FILE);
            client = server.getClient();
            new Thread(() -> {
                try {
                    String userName = setName(client, clientLogger);
                    if (userName != null) {
                        users.put(userName,
                                new PrintWriter(client.getOutputStream(),
                                        true));
                        clientLogger.log("/connect");
                        server.sendToAll(users, userName + ": connect!");
                    }
                    communication(client, userName, clientLogger);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }).start();
        }
    }

    private static String setName(Socket client, Logger logger) throws IOException {
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        if (out == null || in == null) {
            throw new IOException("User didn't connect!");
        } else {
            String name;
            out.println("Enter your name: ");
            if (in.ready()) {
                name = in.readLine();
                if (name.equalsIgnoreCase(EXIT)) {
                    userIsOut(client, "", logger);
                    return null;
                }
                for (String user : users.keySet()) {
                    if (user.equalsIgnoreCase(name)) {
                        out.println("This name is taken!");
                        name = setName(server.getClient(), logger);
                    }
                    logger.setName(name);
                    return name;
                }
            }
        }
        return null;
    }

    private static void userIsOut(Socket client, String user, Logger logger) throws IOException {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
        server.sendToAll(users, user +
                ": disconnect");
        logger.log("/disconnect");
        for (String name : users.keySet()) {
            if (name.equalsIgnoreCase(user)) {
                users.remove(user);
            }
        }
        client.close();
    }

    private static void communication(Socket client, String user, Logger logger) throws IOException {
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        while (true) {
            if (in.ready()) {
                String msg = in.readLine();
                if (msg.equalsIgnoreCase(EXIT)) {
                    userIsOut(client, user, logger);
                    break;
                }
                server.sendToAll(users, msg);
                logger.log(msg);
            }
        }
    }

}
