package unittest.testcases.systems.input.keyboard;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import tech.fastj.input.keyboard.Keys;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeysTests {
    @Test
    void checkFindKeys_shouldNotFail() {
        assertDoesNotThrow(() -> {
            for (Keys key : Keys.values()) {
                Keys equivalentKey = Keys.get(key.name());
                assertEquals(key, equivalentKey, "The equivalent key should match the expected key.");
                assertEquals(key.getKeyCode(), equivalentKey.getKeyCode(), "The equivalent key code should match the expected key code.");
                assertEquals(key.getKeyLocation(), equivalentKey.getKeyLocation(), "The equivalent key location should match the expected key location.");
            }
        });
    }

    @Test
    void tryFindKeys_withInvalidKeyName() {
        String keyName = "Invalid Enter";
        String expectedExceptionMessage = "Couldn't find a key with key name \"" + keyName + "\".";

        Throwable exception = assertThrows(NoSuchElementException.class, () -> Keys.get(keyName));
        assertEquals(expectedExceptionMessage, exception.getMessage(), "The thrown exception's message should match the expected exception message.");
    }
}
