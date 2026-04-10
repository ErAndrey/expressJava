package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"Map<Priority, List<Task<T>>> filterTasksByPriority()\"")
public class FilterTasksByPriorityTest extends Preconditions {

    /**
     * Positive :
     * x1, x2, x3, x4, x5, x6 -> Map.size() = 4
     *
     * Нет задач -> Map.size() = 0
     */

    @Test
    void filterTasksByPriority_ReturnsCorrectlyMap_WhenWeHaveTasks() {
        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x4);
        targetClass.addTaskToList(x5);
        targetClass.addTaskToList(x6);

        assertEquals(4, targetClass.filterTasksByPriority().size());

        assertTrue(targetClass.filterTasksByPriority().containsKey(Priority.LOW));
        assertTrue(targetClass.filterTasksByPriority().containsKey(Priority.HIGH));
        assertTrue(targetClass.filterTasksByPriority().containsKey(Priority.MEDIUM));
        assertTrue(targetClass.filterTasksByPriority().containsKey(Priority.BLOCKER));

        assertEquals(2, targetClass.filterTasksByPriority().get(Priority.LOW).size());
        assertEquals(2, targetClass.filterTasksByPriority().get(Priority.HIGH).size());
        assertEquals(1, targetClass.filterTasksByPriority().get(Priority.MEDIUM).size());
        assertEquals(1, targetClass.filterTasksByPriority().get(Priority.BLOCKER).size());
    }

    @Test
    void filterTasksByPriority_ReturnsEmptyMap_WhenWeHaveNotTask() {
        assertTrue(targetClass.filterTasksByPriority().isEmpty());
    }
}
