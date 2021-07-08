package unittest.testcases.systems.input.keyboard;

import org.junit.jupiter.api.Test;
import tech.fastj.input.keyboard.Keys;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class KeysTests {

    @Test
    void checkGetKeys_shouldNotFail() {
        assertDoesNotThrow(() -> {
            for (Keys key : Keys.values()) {
                Keys equivalentKey = Keys.get(key.name(), key.getKeyCode(), key.getKeyLocation());
                assertEquals(key, equivalentKey, "The equivalent key should match the expected key.");
                assertEquals(key.getKeyCode(), equivalentKey.getKeyCode(), "The equivalent key code should match the expected key code.");
                assertEquals(key.getKeyLocation(), equivalentKey.getKeyLocation(), "The equivalent key location should match the expected key location.");
            }
        });
    }

    @Test
    void checkGetKeys_withInvalidKeyCode_shouldNotReturnUndefined() {
        String validKeyName = Keys.Enter.name();
        int invalid_keyCode = -1;
        int validKeyLocation = Keys.Enter.getKeyLocation();

        Keys expectedKey = Keys.Enter;
        Keys actualKey = Keys.get(validKeyName, invalid_keyCode, validKeyLocation);
        assertNotEquals(Keys.Undefined, actualKey, "Despite the key code being invalid, the key name is correct -- the returned key should not be Keys.Undefined.");
        assertEquals(expectedKey, actualKey, "The actual key's value should match the expected key.");
    }

    @Test
    void checkGetKeys_withInvalidKeyLocation_shouldNotReturnUndefined() {
        String validKeyName = Keys.Enter.name();
        int validKeyCode = Keys.Enter.getKeyCode();
        int invalid_keyLocation = -1;

        Keys expectedKey = Keys.Enter;
        Keys actualKey = Keys.get(validKeyName, validKeyCode, invalid_keyLocation);
        assertNotEquals(Keys.Undefined, actualKey, "Despite the key location being invalid, the key name is correct -- the returned key should not be Keys.Undefined.");
        assertEquals(expectedKey, actualKey, "The actual key's value should match the expected key.");
    }

    @Test
    void checkGetKeys_withInvalidKeyCodeAndInvalidKeyLocation_shouldNotReturnUndefined() {
        String validKeyName = Keys.Enter.name();
        int invalid_keyCode = -1;
        int invalid_keyLocation = -1;

        Keys expectedKey = Keys.Enter;
        Keys actualKey = Keys.get(validKeyName, invalid_keyCode, invalid_keyLocation);
        assertNotEquals(Keys.Undefined, actualKey, "Despite both the key code and key location being invalid, the key name is correct -- the returned key should not be Keys.Undefined.");
        assertEquals(expectedKey, actualKey, "The actual key's value should match the expected key.");
    }

    @Test
    void checkGetKeys_withInvalidKeyName_shouldReturnUndefined() {
        String invalid_keyName = "Invalid Enter";
        int validKeyCode = Keys.Enter.getKeyCode();
        int validKeyLocation = Keys.Enter.getKeyLocation();

        Keys actualKey = Keys.get(invalid_keyName, validKeyCode, validKeyLocation);
        assertEquals(Keys.Undefined, actualKey, "The actual key's value should match Keys.Undefined, since the key name is invalid.");
    }

    @Test
    void checkGetKeys_withInvalidKeyNameAndInvalidKeyCode_shouldReturnUndefined() {
        String invalid_keyName = "Invalid Enter";
        int invalid_keyCode = -1;
        int validKeyLocation = Keys.Enter.getKeyLocation();

        Keys actualKey = Keys.get(invalid_keyName, invalid_keyCode, validKeyLocation);
        assertEquals(Keys.Undefined, actualKey, "The actual key's value should match Keys.Undefined, since the key name is invalid.");
    }

    @Test
    void checkGetKeys_withInvalidKeyNameAndInvalidKeyLocation_shouldReturnUndefined() {
        String invalid_keyName = "Invalid Enter";
        int validKeyCode = Keys.Enter.getKeyCode();
        int invalid_keyLocation = -1;

        Keys actualKey = Keys.get(invalid_keyName, validKeyCode, invalid_keyLocation);
        assertEquals(Keys.Undefined, actualKey, "The actual key's value should match Keys.Undefined, since the key name is invalid.");
    }
}
