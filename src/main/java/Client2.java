import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client2 {

    static String host = "127.0.0.1";

    private static final String SETTINGS_PATH = "src/main/resources/settings.txt";
    private static final String EXIT = "/exit";
    private static final String SET_NAME = "/set_name";
    private static Socket clientSocket = null;
    private static BufferedReader inMess;
    private static PrintWriter outMess;
    private static Scanner scannerConsole;
    private static SettingsManager settingsManager = new SettingsManager(SETTINGS_PATH);

    public static void main(String[] args) throws IOException {
        int port = settingsManager.readPort();
        clientSocket = new Socket(host, port);
        outMess = new PrintWriter(clientSocket.getOutputStream(), true);
        inMess = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        scannerConsole = new Scanner(System.in);

        AtomicBoolean flag = new AtomicBoolean(true);

        new Thread(() -> {
            try {
                while (true) {
                    if (!flag.get()) {
                        inMess.close();
                        clientSocket.close();
                        break;
                    }
                    if (inMess.ready()) {
                        String messFormServer = inMess.readLine();
                        System.out.println(messFormServer);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println("To select a name, enter:\n" + SET_NAME);
            System.out.println("To exit, enter:\n" + EXIT);
            while (true) {
                if (scannerConsole.hasNext()) {
                    String mess = scannerConsole.nextLine();
                    if (mess.equalsIgnoreCase(EXIT)) {
                        outMess.println(mess);
                        scannerConsole.close();
                        outMess.close();
                        flag.set(false);
                        break;
                    }
                    if (mess.equalsIgnoreCase(SET_NAME)) {
                        System.out.print("Enter your name: ");
                        outMess.println(mess);
                        continue;
                    }
                    outMess.println(mess);
                }
            }
        }).start();
    }
}

