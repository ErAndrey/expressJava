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
@DisplayName("Tests for : \"List<Integer> mapToLengths(List<String> words)\"")
public final class MapToLengthsTest extends Preconditions {
    /**
     * Positive :
     * ["Java", "C++", "Go"] ->  [4, 3, 2]
     * ["  ", " ", ""] -> [2, 1, 0]
     *
     * Corner :
     * [] -> []
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(List.of("Java", "C++", "Go")), List.of(4, 3, 2)),
                Arguments.of(new ArrayList<>(List.of("  ", " ", "")), List.of(2, 1, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void mapToLengths_ReturnsCorrectlyResult_WhenInputListWithSomeStrings(List<String> actual, List<Integer> expected) {
        assertEquals(expected, targetClass.mapToLengths(actual));
    }

    @Test
    void mapToLengths_ReturnsEmptyList_WhenInputListWasEmptyToo() {
        assertEquals(List.of(), targetClass.mapToLengths(new ArrayList<>()));
    }

    //toDo [BUG] Не обрабатывается null в аргументе - List<Integer> mapToLengths(List<String> words)
    @Test
    void mapToLengths_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.mapToLengths(null));
    }
}
