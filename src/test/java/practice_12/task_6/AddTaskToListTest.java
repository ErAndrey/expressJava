package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"void addTaskToList(Task<T> task)\"")
public class AddTaskToListTest extends Preconditions {

    /**
     * Positive :
     * id 1 -> Добавлена, размер 1
     * id 2 -> Добавлена, размер 2
     * id 2 -> Не добавлена, размер 2
     *
     * Negative :
     * id null -> IllegalArgumentException
     */

    @Test
    void addTaskToList_CanAddTaskWithUniqueId() {
        assertEquals(0, targetClass.getSizeList());

        targetClass.addTaskToList(x1);
        assertEquals(1, targetClass.getSizeList());

        targetClass.addTaskToList(x2);
        assertEquals(2, targetClass.getSizeList());

        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x6);
        assertEquals(4, targetClass.getSizeList());
    }

    @Test
    void addTaskToList_CantAddEqualIdTask() {
        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x1);
        assertEquals(1, targetClass.getSizeList());
    }

    @Test
    void addTaskToList_ReturnsException_WhenTaskIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.addTaskToList(new Task<>(null, Priority.LOW, LocalDate.of(2026, 4, 15))));
    }

}
