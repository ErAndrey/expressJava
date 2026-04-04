package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"int factorial(int n)\"")
public final class FactorialTest extends Preconditions {
    /**
     *  Positive:
     *  0 -> 1
     *  1 -> 1
     *
     *  2 -> 2
     *  3 -> 6
     *  5 -> 120
     *  7 -> 5040
     *
     *  Corner :
     *  14+ -> ArithmeticException
     *
     *  Negative :
     *  -1 -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 6),
                Arguments.of(5, 120),
                Arguments.of(7, 5040)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void factorial_ReturnsCorrectlyResult_WhenInputIsPositiveAndResultLowerByIntegerMaxValue(int actual, int expected) {
        assertEquals(expected, targetClass.factorial(actual));
    }

    @Test
    void factorial_ReturnsIllegalArgumentException_WhenInputIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.factorial(-1));
    }

    //toDo [BUG] Некорректный результат при переполнении Integer - int factorial(int n)
    @Test
    void factorial_ReturnsArithmeticException_WhenResultMoreThanIntegerMaxValue() {
        assertThrows(ArithmeticException.class, () -> targetClass.factorial(14));
    }
}
