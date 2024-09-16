package entities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private Socket client;
    private ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;
    private String clientName;

    public Server(String path) throws IOException {
        PortReader portReader = new PortReader(path);
        port = portReader.getPort();
        server = new ServerSocket(port);
        System.out.println("Start SERVER in " + port + " port");
    }

    public boolean connectClient() {
        try {
            client = server.accept();
            System.out.println("New user is trying to connect...");
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    private boolean isExit(String command) {
        if (command.equals("/exit")) {
            return true;
        } else return false;
    }

    public boolean closeClient(Socket client) {
        try {
            System.out.println("User disconnected!");
            out.writeUTF("/exit");
            out.flush();
            client.close();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    public boolean closeServer() {
        System.out.println("Server is closed!");
        try {
            server.close();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    public boolean setName() {
        try {
            out.writeUTF("Enter your name: ");
            out.flush();
            clientName = in.readUTF();
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        if (isExit(clientName)) {
            boolean close = closeClient(client);
            if (!close) return false;
        } else if (clientName.isEmpty()) {
            clientName = "Default username";
            System.out.println(clientName + " has connected!");
        }
        return true;
    }

    public String getName() {
        return clientName;
    }

    public int getPort() {
        return port;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getClient() {
        return client;
    }

}
