package practice_12.task_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"synchronized void addGrade(StudentGrade<T> studentGrade)\"")
public class AddGradeTest extends Preconditions {

    /**
     * Positive :
     * > 0.0 -> Добавится
     *
     * Corner :
     * 0.0 -> Добавится
     *
     * Negative :
     * null -> InvalidGradeException
     * < 0.0 -> InvalidGradeException
     */

    @Test
    void addGrade_AddGrade_WhenGradeIsPositive() {
        Thread t1 = new Thread(() -> {
            targetClass.addGrade(new StudentGrade<>("", "", 2.0));
        });
        Thread t2 = new Thread(() -> {
            targetClass.addGrade(new StudentGrade<>("", "", 5.0));
        });
        Thread t3 = new Thread(() -> {
            targetClass.addGrade(new StudentGrade<>("", "", 11.4));
        });
        Thread t4 = new Thread(() -> {
            targetClass.addGrade(new StudentGrade<>("", "", 40.3));
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals(4, targetClass.getStudentGrades().size());
    }

    @Test
    void addGrade_AddGrade_WhenGradeIsZero() {
        targetClass.addGrade(new StudentGrade<>("Andrew", "Math", 0.0));
        assertEquals(1, targetClass.getStudentGrades().size());
    }

    @Test
    void addGrade_ReturnsException_WhenInputIsNull() {
        assertThrows(InvalidGradeException.class, () -> targetClass.addGrade(new StudentGrade<>("Andrew", "Math", null)));
    }

    @Test
    void addGrade_ReturnsException_WhenGradeIsNegative() {
        assertThrows(InvalidGradeException.class, () -> targetClass.addGrade(new StudentGrade<>("Andrew", "Math", -0.1)));
        assertThrows(InvalidGradeException.class, () -> targetClass.addGrade(new StudentGrade<>("Andrew", "Math", -2.0)));
    }
}
