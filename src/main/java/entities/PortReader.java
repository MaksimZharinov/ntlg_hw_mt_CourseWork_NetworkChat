package entities;

import interfaces.Readable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PortReader implements Readable {

    private int port = 0;
    private String path;

    public int getPort() {
        return port;
    }

    PortReader(String path) {
        this.path = path;
        read(path);
    }

    public boolean read(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("port=")) {
                    String[] strings = line.split("=");
                    port = Integer.parseInt(strings[strings.length - 1]);
                    if (port == 0) return false;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found.");
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number format.");
            return false;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}
