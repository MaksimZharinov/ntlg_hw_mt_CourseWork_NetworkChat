import entities.Client;
import entities.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatClient0 {

    private static Client client;
    private static String clientName;

    private static String command;
    private static String response;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static BufferedReader keyboard;

    private static final String SETTINGS_PATH = "src/main/resources/settings.txt";
    private static final String LOG_PATH = "src/main/resources/clients_log.txt";

    public static void main(String[] args) {
        Logger logger = new Logger(LOG_PATH);
        client = new Client(SETTINGS_PATH);
        logger.setName("Username");
        logger.log("User is trying to connect...");
        out = client.getOut();
        in = client.getIn();
        keyboard = client.getKeyboard();
        try {
            setName();
            logger.log("Chose the name: " + clientName);
            logger.setName(clientName);
            while (true) {
                response = in.readUTF();
                if (client.isExit(response)) {
                    logger.log(clientName + " is disconnect");
                    client.closeClient();
                    break;
                }
                System.out.println(response);
                command = keyboard.readLine();
                out.writeUTF(command);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static void setName() throws IOException {
        response = in.readUTF();
        System.out.println(response);
        command = keyboard.readLine();
        if (client.isExit(command)) {
            out.writeUTF(command);
            out.flush();
            response = in.readUTF();
            System.out.println(response);
            client.closeClient();
        } else {
            System.out.println("Your name: " + command);
            clientName = command;
            out.writeUTF(command);
            out.flush();
        }
    }
}
