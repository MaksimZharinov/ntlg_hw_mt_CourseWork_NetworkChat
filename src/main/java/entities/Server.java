package entities;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private int port = -1;
    private Socket client = null;
    private ServerSocket server = null;
    private String message = null;
    private String log_message = null;

    public boolean readPort(String settingsFile) {
        if (settingsFile.isEmpty() || settingsFile == null) {
            return false;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("port=")) {
                    String[] strings = line.split("=");
                    port = Integer.parseInt(strings[strings.length - 1]);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        if (port != -1) {
            return true;
        } else {
            return false;
        }
    }

    public int getPort() {
        return port;
    }

    public boolean startServer() {
        if (port == -1) {
            return false;
        }
        else {
            try {
                server = new ServerSocket(port);
                System.out.println("Start SERVER in port: " + port);
                return true;
            } catch (IOException e) {
                System.err.println(e);
                return false;
            }
        }
    }

    public boolean connectClient() {
        if (server == null) {
            return false;
        }
        try {
            System.out.println("New user is trying to connect...");
            client = server.accept();
            if (client == null) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean sendToAll(ConcurrentHashMap<String, PrintWriter> clients, String msg) {
        for (Map.Entry<String, PrintWriter> entry : clients.entrySet()) {
            PrintWriter out = entry.getValue();
            out.println(msg);
        }
        return true;
    }
}
