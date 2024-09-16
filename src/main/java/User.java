import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private Socket clientSocket;
    private PrintWriter outMess;
    private String name;

    public User(Socket clientSocket, PrintWriter outMess) {
        this.clientSocket = clientSocket;
        this.outMess = outMess;
    }

    public void sendMsg(String msg) {
        outMess.println(msg);
        outMess.flush();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}