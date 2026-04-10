package practice_12.task_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice_7.exceptions.IllegalUserAgeException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"boolean removeEntity(T entity)\"")
public class removeEntityTest extends Preconditions {

    /**
     * Positive :
     * Удалить существующего Entity из листа -> true
     * Удалить другого Entity с теми же данными из листа -> true
     *
     * Corner :
     * Повторно Удалить существующего Entity из листа -> true, false
     *
     * Negative :
     * Удалить Entity из пустого листа -> false
     * Удалить несуществующего Entity из листа -> false
     * Удалить null -> Illegal
     */

    @Test
    void removeEntity_ReturnsTrue_WhenWeRemoveContainedEntity() {
        targetClass.addEntity(x);
        assertEquals(1, targetClass.getEntities().size());

        assertTrue(targetClass.removeEntity(x));
        assertEquals(0, targetClass.getEntities().size());
    }

    @Test
    void removeEntity_ReturnsTrue_WhenWeRemoveAnotherEntityWhichHaveEqualData() {
        targetClass.addEntity(x);
        assertEquals(1, targetClass.getEntities().size());

        assertTrue(targetClass.removeEntity(new Developer("Андрей", 25, true)));
        assertEquals(0, targetClass.getEntities().size());
    }

    @Test
    void removeEntity_ReturnsFalseAfterTrue_WhenWeRemovedContainedEntitySecondTimes() {
        targetClass.addEntity(x);
        targetClass.addEntity(y);
        assertEquals(2, targetClass.getEntities().size());

        assertTrue(targetClass.removeEntity(x));
        assertFalse(targetClass.removeEntity(x));
        assertEquals(1, targetClass.getEntities().size());
    }

    @Test
    void removeEntity_ReturnsFalse_WhenWeRemoveEntityFromEmptyList() {
        assertFalse(targetClass.removeEntity(x));
    }

    @Test
    void removeEntity_ReturnsFalse_WhenWeRemoveNotContainedEntity() {
        targetClass.addEntity(x);
        assertFalse(targetClass.removeEntity(y));
        assertEquals(1, targetClass.getEntities().size());
    }

    @Test
    void removeEntity_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalUserAgeException.class, () -> targetClass.removeEntity(null));
    }
}
