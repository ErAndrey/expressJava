package practice_12.task_1;

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
@DisplayName("Tests for : \"List<T> filterByAge(int from, int to)\"")
public class FilterByAgeTest extends Preconditions {

    public Stream<Arguments> dataForPositiveCases() {
        return Stream.of(
                Arguments.of(20, 30, 3),
                Arguments.of(21, 30, 2),
                Arguments.of(21, 29, 1),
                Arguments.of(10, 15, 0)
        );
    }

    /**
     * Добавляем 20, 25, 30.
     *
     * Positive :
     * Ищем 20, 30 -> 3 элемента
     * Ищем 21, 30 -> 2 элемента
     * Ищем 21, 29 -> 1 элемент
     * Ищем 10, 15 -> 0 элементов
     *
     * Corner :
     * Ищем 20, 20 -> 1 элемент
     *
     * Negative :
     * Ищем у пустого листа -> NoSuch
     * Ищем по невалидным from и to -> Illegal
     */

    @ParameterizedTest
    @MethodSource("dataForPositiveCases")
    void filterByAge_ReturnsCorrectElements_WhenWeFindForAge(int from, int to, int expectedElementCount) {
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        targetClass.addEntity(z);
        assertEquals(expectedElementCount, targetClass.filterByAge(from, to).size());
    }

    @Test
    void filterByAge_ReturnsCorrectElements_WhenWeFindForOneYear() {
        targetClass.addEntity(y);
        assertEquals(1, targetClass.filterByAge(20, 20).size());
    }

    @Test
    void filterByName_ReturnsException_WhenFilteredListIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> targetClass.filterByAge(0, 0));
    }
}
