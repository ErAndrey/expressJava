package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"List<Task<T>> sortTaskByDate()\"")
public class SortTaskByDateTest extends Preconditions {

    /**
     * Positive :
     * Есть задачи >= 2 -> Отсортированный лист по задачам
     *
     * Corner :
     * Нет задач -> Пустой лист
     */

    @Test
    void sortTaskByDate_ReturnsCorrectlySortedListByDate_WhenWeHaveTasks() {
        targetClass.addTaskToList(x3);
        assertEquals(x3, targetClass.sortTaskByDate().getFirst());
        assertEquals(x3, targetClass.sortTaskByDate().getLast());

        targetClass.addTaskToList(x6);
        assertEquals(x6, targetClass.sortTaskByDate().getFirst());
        assertEquals(x3, targetClass.sortTaskByDate().getLast());

        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x4);
        assertEquals(x4, targetClass.sortTaskByDate().getFirst());
        assertEquals(x1, targetClass.sortTaskByDate().getLast());
    }

    @Test
    void sortTaskByDate_ReturnsEmptyList_WhenWeHaveNotTask() {
        assertTrue(targetClass.sortTaskByDate().isEmpty());
    }
}
