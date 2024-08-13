package entities;

import interfaces.Readable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PortReader implements Readable {

    private int port;

    public int getPort() {
        return port;
    }

    public void read() {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/settings.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("port=")) {
                    String[] strings = line.split("=");
                    port = Integer.parseInt(strings[strings.length - 1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number format.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}