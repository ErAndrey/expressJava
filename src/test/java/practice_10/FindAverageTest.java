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
@DisplayName("Tests for : \"double findAverage(int[] numbers)\"")
public final class FindAverageTest extends Preconditions {
    /**
     * Positive :
     * [1, 2, 3, 4, 5] → 3.0
     * [10, 0, 0] -> 3.33 //toDo Можно и привести к сотым ?
     * [-10, 20] -> 5.0
     *
     * Corner :
     * [0] -> 0.0
     * [-3] -> -3.0
     * [12] -> 12.0
     *
     * Negative :
     * [] -> NoSuchElementException
     * null -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 3.0),
                Arguments.of(new int[]{10, 0, 0}, 3.3333333333333335),
                Arguments.of(new int[]{-10, 20}, 5.0)
        );
    }

    private Stream<Arguments> dataForCornerCases() {
        return Stream.of(
                Arguments.of(new int[]{0}, 0.0),
                Arguments.of(new int[]{-3}, -3.0),
                Arguments.of(new int[]{12}, 12.0)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void findAverage_ReturnsCorrectlyResult_WhenArrayContainsNumbers(int[] actual, double expected) {
        assertEquals(expected, targetClass.findAverage(actual));
    }

    @ParameterizedTest
    @MethodSource("dataForCornerCases")
    void findAverage_ReturnsCorrectlyResult_WhenArrayContainsOnlyOneNumber(int[] actual, double expected) {
        assertEquals(expected, targetClass.findAverage(actual));
    }

    @Test
    void findAverage_ReturnsNoSuchElementException_WhenArrayIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> targetClass.findAverage(new int[]{}));
    }

    //toDo Не обрабатывается null в аргументе - double findAverage(int[] numbers)
    @Test
    void findAverage_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.findAverage(null));
    }
}
