package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"boolean isValidPassword(String password)\"")
public final class IsValidPasswordTest extends Preconditions {
    /**
     * Positive :
     * "Password1" → true
     *
     * Negative :
     * length < 8 -> false
     * null -> false
     */

    @ParameterizedTest
    @ValueSource(strings = {"Password1", "Pa0ssw!ord2"})
    void isValidPassword_ReturnsTrue_WhenInputPasswordIsCorrect(String password) {
        assertTrue(targetClass.isValidPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password", "Passwor"})
    void isValidPassword_ReturnsFalse_WhenInputPasswordLengthLowerBy9(String password) {
        assertFalse(targetClass.isValidPassword(password));
    }

    @Test
    void isValidPassword_ReturnsFalse_WhenInputPasswordIsNull() {
        assertFalse(targetClass.isValidPassword(null));
    }
}
