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
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"String reverse(String input)\"")
public final class ReverseTest extends Preconditions {
    /**
     * Positive :
     * "aBc" -> "cBa"
     * "Java0" -> "0avaJ"
     * * ". " -> " ."
     *
     * Corner :
     * "" -> ""
     * "x" -> "x"
     *
     * Negative :
     * null -> null
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of("aBc", "cBa"),
                Arguments.of("Java0", "0avaJ"),
                Arguments.of(". ", " .")
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void reverse_ReturnsCorrectlyString_WhenStringLengthMoreByOne(String actual, String expected) {
        assertEquals(expected, targetClass.reverse(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "x"})
    void reverse_ReturnsCorrectlyString_WhenStringLengthLowerByTwo(String string) {
        assertEquals(string, targetClass.reverse(string));
    }

    @Test
    void reverse_ReturnsNull_WhenInputIsNull() {
        assertNull(targetClass.reverse(null));
    }
}
