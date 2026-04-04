package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Tests for : \"boolean isValidEmail(String email)\"")
public final class IsLeapYearTest extends Preconditions {
    /**
     * Positive :
     * % 400
     * 1200 -> true
     * 400 -> true
     *
     * % 4 && !% 100
     * 2024 -> true
     * 1996 -> true
     * 4 -> true
     *
     * 2026 -> false
     * 399 -> false
     * 401 -> false
     * 3 -> false
     * 5 -> false
     *
     * Negative :
     * <= 0 -> Exception // Для метода boolean isLeapYear(int year) не считаю нужным
     */

    @ParameterizedTest
    @ValueSource(ints = {400, 1200, 1600, 2400})
    void isLeapYear_ReturnsTrue_WhenYearDivOn400(int year) {
        assertTrue(targetClass.isLeapYear(year));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 1996, 2024, 2028})
    void isLeapYear_ReturnsTrue_WhenYearDivOn4AndCantDivOn100(int year) {
        assertTrue(targetClass.isLeapYear(year));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 399, 401, 2026})
    void isLeapYear_ReturnsFalse_WhenYearIsOther(int year) {
        assertFalse(targetClass.isLeapYear(year));
    }
}
