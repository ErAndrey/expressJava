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
@DisplayName("Tests for : \"int countWords(String sentence)\"")
public final class CountWordsTest extends Preconditions {
    /**
     * Positive :
     * "Напишите тесты для метода, который считает количество слов в строке:" -> 10
     * "Раз   два" -> 2
     *
     * Corner :
     * "" -> 0
     * "   " -> 0
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of("Напишите тесты для метода, который считает количество слов в строке:", 10),
                Arguments.of("Раз   два", 2),
                Arguments.of("для метода, который считает количество", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void countWords_ReturnsCorrectlyResult_WhenStringHaveALotOfWords(String actual, int expected) {
        assertEquals(expected, targetClass.countWords(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void countWords_ReturnsZero_WhenStringHaveNotWords(String actual) {
        assertEquals(0, targetClass.countWords(actual));
    }

    //toDo [BUG] Не обрабатываем null в аргументе - int countWords(String sentence)
    @Test
    void countWords_ReturnsIllegalArgumentException_WhenWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.countWords(null));
    }
}
