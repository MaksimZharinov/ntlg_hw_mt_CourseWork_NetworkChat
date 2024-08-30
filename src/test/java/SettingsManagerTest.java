import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsManagerTest {

    private final String SETTINGS_PATH = "src/main/resources/settings.txt";

    @Test
    void readPort() {
        SettingsManager settingsManager = new SettingsManager(SETTINGS_PATH);
        int expected = 8888;

        int result = settingsManager.readPort();

        assertEquals(expected, result);
    }
}