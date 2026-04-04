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
@DisplayName("Tests for : \"int findSecondMax(int[] numbers)\"")
public final class FindSecondMaxTest extends Preconditions {
    /**
     * Positive :
     * [1, 1] -> 1
     * [1, 2] -> 2
     * [-1, 0] -> 0
     * [1, 2, 3] -> 3
     * [-1, -5, -3] -> -1
     * [-1, -5, 0, 5] -> -1
     *
     * Negative :
     * [] -> IllegalArgumentException
     * [1] -> IllegalArgumentException
     * [1, 1, 1] -> NoSuchElementException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 1}, 1),
                Arguments.of(new int[]{1, 2}, 2), //toDo [BUG] Проблема в .skip() - int findSecondMax(int[] numbers)
                Arguments.of(new int[]{-1, 0}, 0), //toDo [BUG] Проблема в .skip() - int findSecondMax(int[] numbers)
                Arguments.of(new int[]{1, 2, 3}, 2),
                Arguments.of(new int[]{-1, -5, -3}, -3),
                Arguments.of(new int[]{-1, -5, 0, 5}, -1) //toDo [BUG] Проблема в .skip() - int findSecondMax(int[] numbers)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void findSecondMax_ReturnsCorrectlyResult_WhenArrayLengthMoreThan1AndAllNumbersAreDistinct(int[] actual, int expected) {
        assertEquals(expected, targetClass.findSecondMax(actual));
    }

    @Test
    void findSecondMax_ReturnsIllegalArgumentException_WhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.findSecondMax(new int[]{}));
    }

    @Test
    void findSecondMax_ReturnsIllegalArgumentException_WhenArrayContainsOneNumber() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.findSecondMax(new int[]{1}));
    }

    @Test
    void findSecondMax_ReturnsNoSuchElementException_WhenArrayContainsOnlyOneDistinctNumberWhenLengthMoreThanTwo() {
        assertThrows(NoSuchElementException.class, () -> targetClass.findSecondMax(new int[]{1, 1, 1}));
    }
}
