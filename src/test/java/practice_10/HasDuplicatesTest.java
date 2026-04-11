package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"boolean hasDuplicates(int[] numbers)\"")
public final class HasDuplicatesTest extends Preconditions {
    /**
     * Positive :
     * [1, 2, 3, 4, 5] → false
     * [1, 2, 2, 3] → true
     *
     * Corner :
     * [0, -0] - true
     * Пустой массив → false
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    @Test
    void hasDuplicates_ReturnsTrue_WhenArrayHaveDuplicates() {
        assertTrue(targetClass.hasDuplicates(new int[]{1, 2, 2, 4}));
    }

    @Test
    void hasDuplicates_ReturnsFalse_WhenArrayHaveNotDuplicates() {
        assertFalse(targetClass.hasDuplicates(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    void hasDuplicates_ReturnsTrue_WhenArrayHaveZeroDuplicates() {
        assertTrue(targetClass.hasDuplicates(new int[]{0, -0}));
    }

    @Test
    void hasDuplicates_ReturnsFalse_WhenArrayIsEmpty() {
        assertFalse(targetClass.hasDuplicates(new int[]{}));
    }

    //toDo [BUG] Не обрабатывается null в аргументе - boolean hasDuplicates(int[] numbers)
    @Test
    void hasDuplicates_ReturnsIllegalArgumentException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.hasDuplicates(null));
    }
}
