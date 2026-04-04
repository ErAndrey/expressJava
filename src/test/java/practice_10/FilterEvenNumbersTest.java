package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"List<Integer> filterEvenNumbers(List<Integer> numbers)\"")
public final class FilterEvenNumbersTest extends Preconditions {
    /**
     * Positive :
     * [1, 2, 3, 4, 0] -> [2, 4, 0]
     * [-1, -2, -3, -5, -0] -> [-2, 0]
     *
     * Corner :
     * [] -> []
     * [1, -3] -> []
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(List.of(1, 2, 3, 4, 0)), List.of(2, 4, 0)),
                Arguments.of(new ArrayList<>(List.of(-1, -2, -3, -5, 0)), List.of(-2, 0))
        );
    }

    private Stream<Arguments> dataForCornerCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(), List.of()),
                Arguments.of(new ArrayList<>(List.of(1, -3)), List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void filterEvenNumbers_ReturnsCorrectlyResult_WhenListContainsEvenNumbers(List<Integer> actual, List<Integer> expected) {
        assertEquals(expected, targetClass.filterEvenNumbers(actual));
    }

    @ParameterizedTest
    @MethodSource("dataForCornerCases")
    void filterEvenNumbers_ReturnsCorrectlyResult_WhenListContainsNoEvenNumbers(List<Integer> actual, List<Integer> expected) {
        assertEquals(expected, targetClass.filterEvenNumbers(actual));
    }

    //toDo [BUG] Не обрабатывается null в аргументе - List<Integer> filterEvenNumbers(List<Integer> numbers)
    @Test
    void filterEvenNumbers_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.filterEvenNumbers(null));
    }
}
