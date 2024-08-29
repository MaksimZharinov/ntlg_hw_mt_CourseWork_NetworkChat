package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerTest {
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = new Logger("src/test/java/testResources/test_log.txt");
    }

    @Test
    void setName() {

        assertFalse(logger.setName(""));
        assertTrue(logger.setName("test_name"));
    }

    @Test
    void log() {

//        logger.setName("test_log");

        assertTrue(logger.log("test_message"));
    }
}