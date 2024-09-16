import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SettingsManager {
    private final String SETTINGS_FILE;

    public SettingsManager(String path) {
        SETTINGS_FILE = path;
    }

    public int readPort() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_FILE))) {

            String line;
            int port;
            while ((line = reader.readLine()) != null) {
                if (line.contains("port=")) {
                    String[] strings = line.split("=");
                    port = Integer.parseInt(strings[strings.length - 1]);
                    return port;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number format.");
        } catch (IOException e) {
            System.err.println(e);
        }
        return -1;
    }
}
