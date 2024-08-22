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

    public Socket connectClient() throws IOException {
        client = server.accept();
        System.out.println("New user try to connect...");
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
        return client;
    }

    public boolean isExit(String command) {
        if (command.equals("/exit")) {
            return true;
        } else return false;
    }

    public void closeClient(Socket client) throws IOException {
        System.out.println("User is disconnect!");
        out.writeUTF("Disconnect!");
        out.flush();
        client.getInputStream().close();
        client.getOutputStream().close();
        client.close();
    }

    public void closeServer() throws IOException {
        System.out.println("Server is closed!");
        server.close();
    }

    public void setName() throws IOException {
        out.writeUTF("Enter your name: ");
        out.flush();
        clientName = in.readUTF();
        if (isExit(clientName)) {
            closeClient(client);
            closeServer();
        } else if (clientName.isEmpty()) {
            clientName = "Default username";
            System.out.println(clientName + " is connected!");
        }
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
