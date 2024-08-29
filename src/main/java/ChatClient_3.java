import entities.Client;
import entities.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClient_3 {

    private static final String HOST = "127.0.0.1";
    private static final String SETTINGS_FILE = "src/main/resources/settings.txt";
    private static final String LOG_FILE = "src/main/resources/log_client_3.txt";
    private static final String EXIT = "/exit";
    private static Client client = new Client();
    private static Logger logger = new Logger(LOG_FILE);
    private static BufferedReader in;
    private static PrintWriter out;
    private static Scanner keyboard;

    public static void main(String[] args) {
        client.readPort(SETTINGS_FILE);
        client.connect(HOST);
        in = client.getIn();
        out = client.getOut();
        keyboard = new Scanner(System.in);
        AtomicBoolean flag = new AtomicBoolean(true);


        new Thread(() -> {
            try {
                while (true) {
                    if (!flag.get()) {
                        in.close();
                        client.closeClient();
                        break;
                    }
                    if (in.ready()) {
                        String messFormServer = in.readLine();
                        System.out.println(messFormServer);
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                if (keyboard.hasNext()) {
                    String mess = keyboard.nextLine();
                    if (mess.equalsIgnoreCase(EXIT)) {
                        out.println(mess);
                        keyboard.close();
                        out.close();
                        flag.set(false);
                        break;
                    }
                    out.println(mess);
                }
            }
        }).start();
    }
}
