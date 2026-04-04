package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests for : \"boolean isAnagram(String str1, String str2)\"")
public final class IsAnagramTest extends Preconditions {
    /**
     * Positive :
     * "listen", "silent" -> true
     * "ttelEr", "letter" -> true
     *
     * "java", "python" → false
     *
     * Corner :
     * "", " " -> true
     * "Abba", "Abba" -> true
     *
     * Negative :
     * null, "python" - false
     * "java", null → false
     * null, null -> false
     */

    private Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of("listen", "silent"),
                Arguments.of("ttelEr", "letter")
        );
    }

    private Stream<Arguments> dataForNegativeCases() {
        return Stream.of(
                Arguments.of(null, "python"),
                Arguments.of("java", null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void isAnagram_ReturnsTrue_WhenWeExpectedIt(String s1, String s2) {
        assertTrue(targetClass.isAnagram(s1, s2));
    }

    @Test
    void isAnagram_ReturnsFalse_WhenWeNotExpectedIt() {
        assertFalse(targetClass.isAnagram("java", "python"));
    }

    @Test
    void isAnagram_ReturnsTrue_WhenStringsEquals() {
        assertTrue(targetClass.isAnagram("Abba", "Abba"));
    }

    @Test
    void isAnagram_ReturnsTrue_WhenStringsHaveNotLetterButHaveSpaces() {
        assertTrue(targetClass.isAnagram("   ", " "));
    }

    @ParameterizedTest
    @MethodSource("dataForNegativeCases")
    void isAnagram_ReturnsFalse_WhenWeExpectedIt(String s1, String s2) {
        assertFalse(targetClass.isAnagram(s1, s2));
    }
}
