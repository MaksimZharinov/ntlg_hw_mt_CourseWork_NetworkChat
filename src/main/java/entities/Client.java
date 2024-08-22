package entities;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class Client {
    private int name;
    private int id;
    private int port;
    private String host = "localhost";
    private Socket client;
    private BufferedReader keyboard;
    private DataInputStream in;
    private DataOutputStream out;
    private String command;
    private String response;

    public Client(String path) {
        PortReader portReader = new PortReader(path);
        port = portReader.getPort();
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        setId();
        setName();
        try {
            client = new Socket(host, port);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            System.out.println("You are connect in chat!");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void closeClient() throws IOException {
        in.close();
        out.close();
        client.close();
    }

    public boolean isExit(String command) {
        if (command.equals("/exit")) {
            return true;
        } else return false;
    }

    public Socket getClient() {
        return client;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public BufferedReader getKeyboard() {
        return keyboard;
    }

    private void setName() {
        Random random = new Random();

        name = random.nextInt();
    }

    private void setId() {
        Random random = new Random();

        id = random.nextInt();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
