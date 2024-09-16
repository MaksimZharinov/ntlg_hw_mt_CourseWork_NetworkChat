package entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortReaderTest {

    private static String settings = "src/main/resources/settings.txt";
    private static int port = 8888;
    private PortReader portReader;

    @BeforeEach
    void setUp() {
        portReader = new PortReader(settings);
    }

    @Test
    void getPort() {

        int result = portReader.getPort();

        assertEquals(port, result);
    }

    @Test
    void read() {

        boolean result = portReader.read(settings);

        assertTrue(result);
    }
}