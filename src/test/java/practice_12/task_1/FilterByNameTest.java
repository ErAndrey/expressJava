package practice_12.task_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"List<T> filterByName(String name)\"")
public class FilterByNameTest extends Preconditions {

    /**
     * Positive
     * 1 "Андрей" : "Андрей" -> Найден 1 ["Андрей"]
     * 2 "Данил" : "Данил" -> Найдено 2 ["Данил", "Данил"]
     * ... : "xxx" -> Найдено 0
     *
     * Corner :
     * 1 "Саша", 1 "СаШа", 1 "сашА" : "саша" -> Найдено 3 ["Саша", "сашА", "СаШа"]
     * ... : "" -> Найдено 0
     *
     * Negative :
     * Ищем у пустого листа -> NoSuch
     * Ищем по null -> Illegal
     */

    @Test
    void filterByName_ReturnsOneElement_WhenOnlyOneFindElementContainsInList() {
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        assertEquals(2, targetClass.getEntities().size());
        assertEquals(1, targetClass.filterByName("Андрей").size());
    }

    @Test
    void filterByName_ReturnsTwoElements() {
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        targetClass.addEntity(new Developer("Данил", 1, false));
        assertEquals(3, targetClass.getEntities().size());
        assertEquals(2, targetClass.filterByName("Данил").size());
    }

    @Test
    void filterByName_ReturnsNoElements() {
        targetClass.addEntity(x);
        assertTrue(targetClass.filterByName("xxx").isEmpty());
    }

    @Test
    void filterByName_ReturnsAllMatchElementsWithIgnoreCase() {
        targetClass.addEntity(z);
        targetClass.addEntity(new Developer("СаШа", 1, true));
        targetClass.addEntity(new Developer("сашА", 1, false));
        assertEquals(3, targetClass.filterByName("саша").size());
    }

    @Test
    void filterByName_ReturnsNoElements_WhenInputIsEmpty() {
        targetClass.addEntity(x);
        assertTrue(targetClass.filterByName("").isEmpty());
    }

    @Test
    void filterByName_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.filterByName(null));
    }

    @Test
    void filterByName_ReturnsEmptyList_WhenFilteredListIsEmpty() {
        assertTrue(targetClass.filterByName("Name").isEmpty());
    }
}
