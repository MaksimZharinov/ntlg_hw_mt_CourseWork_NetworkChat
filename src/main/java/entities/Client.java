package entities;

import java.io.*;
import java.net.Socket;

public class Client {
    private int port = -1;
    private Socket client = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String clientName = null;

    public boolean readPort(String settingsFile) {
        if (settingsFile.isEmpty() || settingsFile == null) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("port=")) {
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

    public boolean startClient(String host) {
        if (port == -1) {
            return false;
        }
        if (client != null) {
            return false;
        }
        try {
            client = new Socket(host, port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("You are connected!");
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean setClientName(String clientName) {
        this.clientName = clientName;
        return true;
    }

    public boolean send(String msg) {
        if (out == null) {
            return false;
        }
        out.println(msg);
        out.flush();
        return true;
    }

    public Socket getClient() {
        return client;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public String getClientName() {
        return clientName;
    }
}
