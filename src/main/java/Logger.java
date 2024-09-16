import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Logger {
    private final String LOGFILE;
    private DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .toFormatter();

    public Logger(String path) {
        LOGFILE = path;
    }

    public synchronized boolean log(String name, String msg) {
        try (BufferedWriter writer = new BufferedWriter
                (new FileWriter(LOGFILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            writer.write(now.format(formatter) +
                    " " + name +
                    ": " + msg + "\n");
            return true;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
    }
}
