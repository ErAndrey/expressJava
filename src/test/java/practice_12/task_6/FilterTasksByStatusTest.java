package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests for : \"Map<String, List<Task<T>>> filterTasksByStatus()\"")
public class FilterTasksByStatusTest extends Preconditions {

    /**
     * Если только открытые (1) -> Map.size(1), List.size(1)
     * Если только закрытые (2) -> Map.size(1), List.size(2)
     * Если открытые (1) и закрытые (3) -> Map.size(2), открытые.size(1) и закрытые.size(3)
     *
     * Corner :
     * Пустая мапа -> Map.size(0)
     */

    @Test
    void filterTasksByStatus_WhenListContainsOnlyOpenTasks() {
        targetClass.addTaskToList(x1);

        assertEquals(1, targetClass.filterTasksByStatus().size());
        assertEquals(1, targetClass.filterTasksByStatus().get("Открытые").size());
    }

    @Test
    void filterTasksByStatus_WhenListContainsOnlyClosedTasks() {
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);

        x2.closeTask();
        x3.closeTask();

        assertEquals(1, targetClass.filterTasksByStatus().size());
        assertEquals(2, targetClass.filterTasksByStatus().get("Закрытые").size());
    }

    @Test
    void filterTasksByStatus_WhenListContainsOtherTasks() {
        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x4);

        x2.closeTask();
        x3.closeTask();
        x4.closeTask();

        assertEquals(2, targetClass.filterTasksByStatus().size());
        assertEquals(1, targetClass.filterTasksByStatus().get("Открытые").size());
        assertEquals(3, targetClass.filterTasksByStatus().get("Закрытые").size());
    }

    @Test
    void filterTasksByStatus_WhenListNpContainsTasks() {
        assertEquals(0, targetClass.filterTasksByStatus().size());
    }
}
