package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private String expectedName = "Expected Name";
    private Logger logger;
    private String path = "src/test/java/testResources/test_log.txt";

    @BeforeEach
    void setUp() {
        logger = new Logger(path);
    }

    @Test
    void setName() {
        boolean result = logger.setName(expectedName);

        assertTrue(result);
    }

    @Test
    void log() {
        boolean result = logger.log("test message");

        assertTrue(result);
    }
}