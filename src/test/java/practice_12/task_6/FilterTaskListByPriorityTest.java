package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"List<Task<T>> filterTaskListByPriority(Priority priority)\"")
public class FilterTaskListByPriorityTest extends Preconditions {

    /**
     * Positive :
     * LOW -> 2
     * HIGH -> 2
     * MEDIUM -> 1
     * BLOCKER -> 1
     *
     * Когда нет задач в таком приоритете -> Пусто
     *
     * Negative :
     * null -> Illegal
     */

    @Test
    void filterTaskListByPriority_ReturnsCorrectlyTasksList_WhenWeHaveThisTasks() {
        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x4);
        targetClass.addTaskToList(x5);
        targetClass.addTaskToList(x6);

        assertEquals(2, targetClass.filterTaskListByPriority(Priority.LOW).size());
        assertEquals(2, targetClass.filterTaskListByPriority(Priority.HIGH).size());
        assertEquals(1, targetClass.filterTaskListByPriority(Priority.MEDIUM).size());
        assertEquals(1, targetClass.filterTaskListByPriority(Priority.BLOCKER).size());

        assertEquals(x6, targetClass.filterTaskListByPriority(Priority.BLOCKER).getFirst());
    }

    @Test
    void filterTaskListByPriority_ReturnsEmptyList_WhenWeHaveNotTask() {
        assertTrue(targetClass.filterTaskListByPriority(Priority.BLOCKER).isEmpty());
    }

    @Test
    void filterTaskListByPriority_ReturnsException_InputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.filterTaskListByPriority(null));
    }
}
