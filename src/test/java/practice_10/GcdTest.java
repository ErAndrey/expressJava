package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"int gcd(int a, int b)\"")
public final class GcdTest extends Preconditions {
    /**
     * Positive :
     * 24, 36 → 12
     * 101, 103 → 1
     * 0, 10 → 10
     *
     * Corner :
     * 0, 0 -> 0
     * 12, 12 -> 12
     * -3, -3 -> -3
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(24, 36, 12),
                Arguments.of(101, 103, 1),
                Arguments.of(0, 10, 10)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void gcd_ReturnsCorrectlyResult_WhenWeHaveNoEqualNumbers(int a, int b, int expected) {
        assertEquals(expected, targetClass.gcd(a, b));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 12, -3})
    void gcd_ReturnsCorrectlyResult_WhenWeHaveEqualNumbers(int number) {
        assertEquals(number, targetClass.gcd(number, number));
    }
}
