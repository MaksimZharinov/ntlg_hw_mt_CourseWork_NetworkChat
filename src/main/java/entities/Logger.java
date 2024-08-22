package entities;

import interfaces.Loggable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Logger implements Loggable {

    private String path;
    private String name;
    private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR,2)
            .toFormatter();
    public Logger(String path) {
        this.path = path;
    }

    public boolean setName(String name) {
        this.name = name;
        if (this.name.isEmpty()) return false;
        else return true;
    }

    @Override
    public boolean log(String msg) {
        try (BufferedWriter writer = new BufferedWriter
                (new FileWriter(path, true))) {
            LocalDateTime now = LocalDateTime.now();
            writer.write(name +
                    " " + now.format(formatter) +
                    ": " + msg + "\n");
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}
