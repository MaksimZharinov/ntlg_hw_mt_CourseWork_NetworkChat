import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(new Socket(), new PrintWriter(System.out));
    }

    @Test
    void sendMsg() {
        user.sendMsg("test_mess");
    }

    @Test
    void setName() {
        String expected = "test_name";

        user.setName("test_name");
        String result = user.getName();

        assertEquals(expected, result);
    }

    @Test
    void getName() {
        assertNull(user.getName());

        user.setName("not_null");

        assertNotNull(user.getName());
    }
}