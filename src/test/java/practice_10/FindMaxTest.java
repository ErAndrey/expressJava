package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"int findMax(int[] numbers)\"")
public final class FindMaxTest extends Preconditions {
    /**
     * Positive :
     * [1, 2, 3, 4] -> 4
     * [-1, -2, -3, -4] -> -1
     * [-5, -10, 0, -4] -> 0
     * Corner :
     * [1] -> 1
     * [0] -> 0
     * [-3] -> -3
     * Negative :
     * [] -> Exception
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4}, 4),
                Arguments.of(new int[]{-1, -2, -3, -4}, -1),
                Arguments.of(new int[]{-5, -10, 0, -4}, 0)
        );
    }

    private Stream<Arguments> dataForCornerCases() {
        return Stream.of(
                Arguments.of(new int[]{1}, 1),
                Arguments.of(new int[]{0}, 0),
                Arguments.of(new int[]{-3}, -3)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void findMax_ReturnsCorrectlyNumber_WhenArrayHavePositiveNumbers(int[] actual,int expected) {
        assertEquals(expected, targetClass.findMax(actual));
    }

    @ParameterizedTest
    @MethodSource("dataForCornerCases")
    void findMax_ReturnsCorrectlyNumber_WhenArrayHaveOneNumber(int[] actual,int expected) {
        assertEquals(expected, targetClass.findMax(actual));
    }

    @Test
    void findMax_ReturnsException_WhenArrayIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> targetClass.findMax(new int[]{}));
    }
}
