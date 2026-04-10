package practice_12.task_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"List<T> filterByActive(boolean isActive)\"")
public class FilterByActiveTest extends Preconditions {
    @Test
    void filterByActive_ReturnsCorrectElements_WhoIsActive(){
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        targetClass.addEntity(z);

        assertEquals(1, targetClass.filterByActive(true).size());
    }

    @Test
    void filterByActive_ReturnsCorrectElements_WhoIsNotActive(){
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        targetClass.addEntity(z);

        assertEquals(2, targetClass.filterByActive(false).size());
    }

    @Test
    void filterByActive_ReturnsException_WhenFilteredListIsEmpty() {
        assertThrows(NoSuchElementException.class, () -> targetClass.filterByActive(false));
    }
}
