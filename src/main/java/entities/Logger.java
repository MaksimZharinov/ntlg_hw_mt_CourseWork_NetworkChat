package entities;

import interfaces.Loggable;

public class Logger implements Loggable {

    private String path;

    public Logger(String path) {
        this.path = path;
    }

    @Override
    public void log(String msg) {

    }
}
