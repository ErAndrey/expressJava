package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"List<String> sortByLength(List<String> words)\"")
public final class SortByLengthTest extends Preconditions {
    /**
     * Positive :
     * ["Java", "C", "Python"] → ["C", "Java", "Python"]
     *
     * Corner :
     * ["aa", "bB", "  "] -> ["aa", "bB", "  "]
     * [] -> []
     *
     * Negative :
     * null -> IllegalArgumentException
     */


    @Test
    void sortByLength_ReturnsCorrectlyResult_WhenListContainsNoDistinctLengthString() {
        assertEquals(List.of("C", "Java", "Python"), targetClass.sortByLength(new ArrayList<>(List.of("Java", "C", "Python"))));
    }

    @Test
    void sortByLength_ReturnsInitialList_WhenListContainsDistinctLengthString() {
        assertEquals(List.of("aa", "bB", "  "), targetClass.sortByLength(new ArrayList<>(List.of("aa", "bB", "  "))));
    }

    @Test
    void sortByLength_ReturnsEmptyList_WhenListWasEmpty() {
        assertEquals(List.of(), targetClass.sortByLength(new ArrayList<>()));
    }

    //toDo [BUG] Не обрабатывается null в аргументе - List<String> sortByLength(List<String> words)
    @Test
    void sortByLength_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.sortByLength(null));
    }
}
