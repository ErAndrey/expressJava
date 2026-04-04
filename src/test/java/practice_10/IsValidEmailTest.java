package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"boolean isValidEmail(String email)\"")
public final class IsValidEmailTest extends Preconditions {
    /**
     * Positive :
     * "a.A@asd.ru" -> true
     * "a@a.com" -> true
     * "A.ru" -> false
     * "a@bru" -> false
     *
     * Corner :
     * "" -> false
     *
     * Negative :
     * null -> false
     */

    @ParameterizedTest
    @ValueSource(strings = {"a.A@asd.ru", "a@a.com"})
    void isValidEmail_ReturnsTrue_WhenInputIsValidEmail(String email) {
        assertTrue(targetClass.isValidEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A.ru", "a@bru"})
    void isValidEmail_ReturnsFalse_WhenInputIsInvalidEmail(String email) {
        assertFalse(targetClass.isValidEmail(email));
    }

    @Test
    void isValidEmail_ReturnsFalse_WhenInputIsEmptyString() {
        assertFalse(targetClass.isValidEmail(""));
    }

    @Test
    void isValidEmail_ReturnsFalse_WhenInputIsNull() {
        assertFalse(targetClass.isValidEmail(null));
    }
}
