import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private static final String LOGFILE_PATH = "src/main/resources/logfile.txt";
    private static final String SETTINGS_PATH = "src/main/resources/settings.txt";
    private static final String EXIT = "/exit";
    private static final String SET_NAME = "/set_name";
    private static Map<Integer, User> users = new HashMap<>();
    private static Logger logger = new Logger(LOGFILE_PATH);
    private static SettingsManager settingsManager = new SettingsManager(SETTINGS_PATH);

    public static void main(String[] args) {
        int port = settingsManager.readPort();
        System.out.println("Start server in port: " + port);
        logger.log("SERVER", "Start server in port: " + port);


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.log("SERVER", "New user is connected: " +
                            clientSocket.getPort());
                    sendMessToAll("New user is connected: " +
                            clientSocket.getPort());
                    new Thread(() -> {
                        try (PrintWriter out = new PrintWriter(
                                clientSocket.getOutputStream(),
                                true)) {
                            User user = new User(clientSocket, out);
                            users.put(clientSocket.getPort(), user);
                            System.out.println(user);
                            waitMessAndSend(clientSocket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void sendMessToAll(String mess) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            entry.getValue().sendMsg(mess);
            System.out.println("Sent");
        }
    }

    public static void waitMessAndSend(Socket clientSocket) {
        try (Scanner inMess = new Scanner(clientSocket.getInputStream())) {
            while (true) {
                int port = clientSocket.getPort();
                User user = users.get(port);
                if (user == null) break;
                String name = user.getName();
                if (name == null) {
                    name = Integer.toString(port);
                }
                if (inMess.hasNext()) {
                    String mess = inMess.nextLine();
                    switch (mess) {
                        case EXIT:
                            for (Integer key : users.keySet()) {
                                if (port == key) {
                                    users.remove(port);
                                    logger.log(name, "has disconnect");
                                    sendMessToAll(name + " has disconnect!");
                                }
                            }
                            break;
                        case SET_NAME:
                            if (inMess.hasNext()) {
                                String selectName = inMess.nextLine();
                                user.setName(selectName);
                                logger.log(name, " select name: " +
                                        selectName);
                                sendMessToAll(name + " select name: " +
                                        selectName);
                            }
                            break;
                        default:
                            logger.log(name, mess);
                            sendMessToAll(name + ": " + mess);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}