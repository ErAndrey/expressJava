package practice_12.task_6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"synchronized boolean removeTaskFromList(T taskId)\"")
public class RemoveTaskFromListTest extends Preconditions {

    /**
     * Positive :
     * Удаляем несколько имеющихся тасок (3/4) -> удалили 3 таски / 4 -> true
     *
     * Corner :
     * Удаляем таску, Id Которой нет -> false
     *
     * Negative :
     * null id -> IllegalArgumentException
     */

    @Test
    void removeTaskFromList_ReturnsTrue_WhenWeDeleteContainedTask() {
        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x4);
        targetClass.addTaskToList(x5);
        targetClass.addTaskToList(x6);

        targetClass.addTaskToList(x1);
        targetClass.addTaskToList(x2);
        targetClass.addTaskToList(x3);
        targetClass.addTaskToList(x4);
        targetClass.addTaskToList(x5);
        targetClass.addTaskToList(x6);

        assertEquals(6, targetClass.getSizeList());

        Thread t1 = new Thread(() -> {
            targetClass.removeTaskFromList(1);
            targetClass.removeTaskFromList(4);
        });

        Thread t2 = new Thread(() -> {
            targetClass.removeTaskFromList(2);
            targetClass.removeTaskFromList(5);
        });

        Thread t3 = new Thread(() -> {
            targetClass.removeTaskFromList(3);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(1, targetClass.getSizeList());

        assertTrue(targetClass.removeTaskFromList(6));
        assertEquals(0, targetClass.getSizeList());
    }

    @Test
    void removeTaskFromList_ReturnsFalse_WhenWeDeleteNotContainedTask() {
        assertEquals(0, targetClass.getSizeList());
        assertFalse(targetClass.removeTaskFromList(1));
    }

    @Test
    void removeTaskFromList_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.removeTaskFromList(null));
    }
}
