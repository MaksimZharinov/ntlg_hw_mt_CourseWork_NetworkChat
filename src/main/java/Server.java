import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private static Map<Integer, User> users = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Start server");
        int port = 8090;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    sendMessToAll("New user is connected: " + clientSocket.getPort());
                    new Thread(() -> {
                        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { // ����� ������ � �����
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
                if (inMess.hasNext()) {
                    String mess = inMess.nextLine();
                    switch (mess) {
                        default:
                            sendMessToAll(clientSocket.getPort() + ": " + mess);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
