package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"String[] splitString(String input, String delimiter)\"")
public final class SplitStringTest extends Preconditions {
    /**
     * Positive :
     * "Java,Python,C++", "," → ["Java", "Python", "C++"]
     *
     * Corner :
     * "", "," → [""]
     * "word", "," → ["word"]
     *
     * Negative :
     * null, "" -> IllegalArgumentException
     * "", null -> IllegalArgumentException
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of("Java,Python,C++", ",", new String[]{"Java", "Python", "C++"})
        );
    }

    private Stream<Arguments> dataForCornerCases() {
        return Stream.of(
                Arguments.of("", ",", new String[]{""}),
                Arguments.of("word", ",", new String[]{"word"})
        );
    }

    private Stream<Arguments> dataForNegativeCases() {
        return Stream.of(
                Arguments.of(null, ","),
                Arguments.of("word", null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void splitString_ReturnsCorrectlyResult_WhenWeHaveNoEmptyStringWithDelimiterWhichContainsInInitialString(String input, String delimiter, String[] expected) {
        assertArrayEquals(expected, targetClass.splitString(input, delimiter));
    }

    @ParameterizedTest
    @MethodSource("dataForCornerCases")
    void splitString_ReturnsCorrectlyResult_WhenWeHaveAStringWithDelimiterWhichNoContainsInInitialString(String input, String delimiter, String[] expected) {
        assertArrayEquals(expected, targetClass.splitString(input, delimiter));
    }

    //toDo [BUG] Не обрабатывается null в аргументе - String[] splitString(String input, String delimiter)
    @ParameterizedTest
    @MethodSource("dataForCornerCases")
    void splitString_ReturnsIllegalArgumentException_WhenInputStringOrDelimiterIsNull(String input, String delimiter) {
        assertThrows(IllegalArgumentException.class, () -> targetClass.splitString(input, delimiter));
    }
}
