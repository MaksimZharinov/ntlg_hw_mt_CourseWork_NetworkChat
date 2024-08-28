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

    public int getPort() {
        return port;
    }

    public Socket getClient() {
        return client;
    }

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
                System.out.println("New user is connected!");
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
            out.flush();
        }
        return true;
    }

    public boolean closeClient() {
        if (client != null) {
            try {
                client.close();
                return true;
            } catch (IOException e) {
                System.err.println(e);
                return false;
            }
        } else {
            System.out.println("Client didn't connect");
            return true;
        }
    }

    public boolean closeServer() {
        if (server != null) {
            try {
                server.close();
                return true;
            } catch (IOException e) {
                System.err.println(e);
                return false;
            }
        } else {
            System.out.println("Server didn't start");
            return true;
        }
    }
}
