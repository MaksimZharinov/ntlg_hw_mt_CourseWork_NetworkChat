package entities;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private Socket client;
    private ServerSocket server;
    private DataInputStream in;
    private DataOutputStream out;

    public Server(String path) {
        PortReader portReader = new PortReader(path);
        port = portReader.getPort();
        try {
            server = new ServerSocket(port);
            System.out.println("Start SERVER in " + port + " port");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public Socket connectClient() {
        try {
            client = server.accept();
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println(e);
        }
        return client;
    }

    public String clientName() {
        String name = null;
        try {
            out.writeUTF("Enter your name: ");
            out.flush();
            name = in.readUTF();
        } catch (IOException e) {
            System.err.println(e);
        }
        return name;
    }

    public void closeClient(Socket client,
                            BufferedReader in,
                            BufferedWriter out) {
        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            System.err.println(e);
        }
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

}
