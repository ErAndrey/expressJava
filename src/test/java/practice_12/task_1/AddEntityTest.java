package practice_12.task_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import practice_7.exceptions.IllegalUserAgeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"void addEntity(T entity)\"")
public class AddEntityTest extends Preconditions {

    /**
     * Positive :
     * Размер листа 0, Добавить 1 Entity x -> Размер листа 1, Добавлен 1 Entity x
     * Размер листа 0, Добавить 2 Entity x, y -> Размер листа 2
     *
     * Corner :
     * Размер листа 0, Добавить 2 Entity x, x -> Размер листа 1, Добавлен 1 Entity x
     * Размер листа 0, Добавить 2 Entity x, y (Одинаковые данные) -> Размер листа 1, Добавлен 1 Entity x
     *
     * Negative :
     * Добавляем null -> IllegalArgumentException
     */

    @Test
    void addEntity_CanAddOneEntity_WhenListIsEmpty() {
        assertEquals(0, targetClass.getEntities().size());
        targetClass.addEntity(x);

        assertEquals(1, targetClass.getEntities().size());
        Developer actual = targetClass.getEntities().getFirst();

        assertEquals(x, actual);

        assertEquals(x.getName(), actual.getName());
        assertEquals(x.getAge(), actual.getAge());
        assertEquals(x.isActive(), actual.isActive());
    }

    @Test
    void addEntity_CanAddSecondUniqueEntity_WhenListIsNotEmpty() {
        targetClass.addEntity(x);
        targetClass.addEntity(y);

        assertEquals(2, targetClass.getEntities().size());
    }

    @Test
    void addEntity_CantAddTwoEqualEntity_WhenTheirLinksIsEqual() {
        targetClass.addEntity(x);
        targetClass.addEntity(x);

        assertEquals(1, targetClass.getEntities().size());
    }

    @Test
    void addEntity_CantAddTwoEqualEntity_WhenTheirDataIsEqual() {
        Developer z = new Developer("Андрей", 25, true);
        targetClass.addEntity(x);
        targetClass.addEntity(z);

        assertEquals(1, targetClass.getEntities().size());
    }

    @Test
    void addEntity_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addEntity(null));
    }
}
