import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    private final String LOGFILE_PATH = "src/test/resources/logfileTest.txt";

    @Test
    void log() {
        Logger logger = new Logger(LOGFILE_PATH);

        boolean result = logger.log("test_name", "test_mess");

        assertTrue(result);
    }
}