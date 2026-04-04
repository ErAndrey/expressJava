package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"public int countVowels(String input)\"")
public final class CountVowelsTest extends Preconditions {
    /**
     * Positive :
     * "aEiOu" -> 5
     * "abcdA" -> 2
     * "o" -> 1
     *
     * Corner :
     * "nbMc" -> 0
     * "" -> 0
     *
     * Negative :
     * null -> Exception
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of("aEiOu", 5),
                Arguments.of("abcdA", 2),
                Arguments.of("o", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void countVowels_ReturnsCorrectlyCount_WhenInputHaveAVowels(String actual, int expected) {
        assertEquals(expected, targetClass.countVowels(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"nbMc", ""})
    void countVowels_ReturnsCorrectlyCount_WhenInputHaveNotAVowels(String actual) {
        assertEquals(0, targetClass.countVowels(actual));
    }

    @Test
    void countVowels_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.countVowels(null));
    }
}
