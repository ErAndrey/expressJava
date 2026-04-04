package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"public boolean isEven(int number)\"")
public final class IsEvenTest extends Preconditions {
    /**
     * Positive :
     * 2 -> true
     * -4 -> true
     * 1 -> false
     * -3 -> false
     *
     * Corner :
     * 0 -> true
     */

    @ParameterizedTest
    @ValueSource(ints = {2, -4})
    void isEven_ReturnsTrue_WhenNumbersIsEven(int number) {
        assertTrue(targetClass.isEven(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, -3})
    void isEven_ReturnsFalse_WhenNumbersIsNotEven(int number) {
        assertFalse(targetClass.isEven(number));
    }

    @Test
    void isEven_ReturnsTrue_WhenNumbersIsZero() {
        assertTrue(targetClass.isEven(0));
    }
}
