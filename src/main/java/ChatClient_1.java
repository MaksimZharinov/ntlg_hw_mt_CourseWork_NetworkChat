import entities.Client;
import entities.Logger;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class ChatClient_1 {

    private static final String HOST = "127.0.0.1";
    private static final String SETTINGS_FILE = "src/main/resources/settings.txt";
    private static final String LOG_FILE = "src/main/resources/log_client_1.txt";
    private static final String EXIT = "/exit";
    private static Client client = new Client();
    private static Logger logger = new Logger(LOG_FILE);
    private static BufferedReader in;
    private static PrintWriter out;
    private static Scanner keyboard;

    public static void main(String[] args) {

    }
}
