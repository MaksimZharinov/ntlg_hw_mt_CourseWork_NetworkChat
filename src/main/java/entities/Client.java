package entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private int port = -1;
    private String clientName = null;
    private Socket client = null;

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
        try {
            client = new Socket(host, port);
            System.out.println("You are connected!");
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
}
