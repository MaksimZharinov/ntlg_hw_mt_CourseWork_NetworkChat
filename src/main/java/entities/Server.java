package entities;

public class Server {

    private int port;

    public Server(String path) {
        PortReader portReader = new PortReader(path);
        port = portReader.getPort();
    }

    public int getPort() {
        return port;
    }

}
