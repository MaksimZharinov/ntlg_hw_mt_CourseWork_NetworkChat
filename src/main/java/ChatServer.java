import entities.Logger;
import entities.Server;

public class ChatServer {
    private static final String SETTINGS_FILE = "src/main/resources/settings.txt";
    private static final String LOG_FILE = "src/main/resources/log_server.txt";
    private static Server server = new Server();
    private static Logger logger = new Logger(LOG_FILE);

    public static void main(String[] args) {

    }
}
