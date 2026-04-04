package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"boolean isValidPhoneNumber(String phone)\"")
public final class IsValidPhoneNumberTest extends Preconditions {
    /**
     * Positive :
     * "+1 1234567890" -> true
     * "+12 1234567890" -> true
     * "+123 1234567890" -> true
     *
     * "+1 123456789" -> false // \\d{10}
     * "+12 12345678901" -> false // \\d{10}
     * "123 1234567890" -> false
     * "+ 1234567890" -> false // \\d{1,3}
     * "+1234 1234567890" -> false // \\d{1,3}
     *
     * Corner :
     * "" -> false
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    @ParameterizedTest
    @ValueSource(strings = {"+1 1234567890", "+12 1234567890", "+123 1234567890"})
    void isValidPhoneNumber_ReturnsTrue_WhenPhoneNumberIsCorrect(String phone) {
        assertTrue(targetClass.isValidPhoneNumber(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+1 123456789",  "+12 12345678901", "123 1234567890", "+ 1234567890", "+1234 1234567890"})
    void isValidPhoneNumber_ReturnsFalse_WhenPhoneNumberIsIncorrect(String phone) {
        assertFalse(targetClass.isValidPhoneNumber(phone));
    }

    @Test
    void isValidPhoneNumber_ReturnsFalse_WhenInputIsEmpty() {
        assertFalse(targetClass.isValidPhoneNumber(""));
    }

    //ToDo [BUG] Не обрабатывается null в аргументе - boolean isValidPhoneNumber(String phone)
    @Test
    void isValidPhoneNumber_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.isValidPhoneNumber(null));
    }
}
