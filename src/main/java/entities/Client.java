package entities;

import java.io.*;
import java.net.Socket;

public class Client {
    private int port;
    private String host = "localhost";
    private Socket client;
    private BufferedReader keyboard;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private String command;

    public Client(String path) {
        PortReader portReader = new PortReader(path);
        port = portReader.getPort();
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        try {
            client = new Socket(host, port);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            System.out.println("Enter your name: ");
            name = keyboard.readLine();
            out.writeUTF(name);
            out.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
