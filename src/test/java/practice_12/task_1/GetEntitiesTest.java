package practice_12.task_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"List<T> getEntities()\"")
public class GetEntitiesTest extends Preconditions {

    /**
     * Positive :
     * Возвращается пустой лист
     * Возвращается лист с одним элементом
     * Возвращается лист с двумя элементом
     *
     * Corner :
     * Возращаемый лист нельзя изменить
     */

    @Test
    void getEntities_ReturnsEmptyList() {
        assertTrue(targetClass.getEntities().isEmpty());
    }

    @Test
    void getEntities_ReturnsListWithOneElement_AfterWeAddedHim() {
        targetClass.addEntity(x);
        assertEquals(1, targetClass.getEntities().size());
    }

    @Test
    void getEntities_ReturnsListWithTwoElements_AfterWeAddedTheir() {
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        assertEquals(2, targetClass.getEntities().size());
    }

    @Test
    void getEntities_ReturnsList_WhichWeCanChange() {
        assertThrows(UnsupportedOperationException.class, () -> targetClass.getEntities().add(x));
    }
}
