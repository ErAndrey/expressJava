package practice_12.task_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"double getAverageMarkForSubject(String subject)\"")
public class GetAverageMarkForSubjectTest extends Preconditions {
    private StudentGrade<Double> x;
    private StudentGrade<Double> y;
    private StudentGrade<Double> z;
    private StudentGrade<Double> c;

    @BeforeEach
    void setUp() {
        this.x = new StudentGrade<>("Andrew", "Math", 2.0);
        this.y = new StudentGrade<>("Mila", "Math", 5.0);
        this.z = new StudentGrade<>("Danila", "Math", 11.4);
        this.c = new StudentGrade<>("Sasha", "Geo", 40.3);
    }

    /**
     * Positive :
     * x, c : Math -> 2.0
     * x, y, c : Math -> 3.5
     * x, y, z, c : Math -> 6.13
     *
     * Corner :
     * "?" -> NoSuchFieldException
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    @Test
    void getAverageMarkForSubject_ReturnsCorrectAverage_WhenWeHaveOneGradeForSubject() {
        targetClass.addGrade(x);
        targetClass.addGrade(c);
        assertEquals(2.0, targetClass.getAverageMarkForSubject("Math"));
    }

    @Test
    void getAverageMarkForSubject_ReturnsCorrectAverage_WhenWeHaveTwoGradeForSubject() {
        targetClass.addGrade(x);
        targetClass.addGrade(y);
        targetClass.addGrade(c);
        assertEquals(3.5, targetClass.getAverageMarkForSubject("Math"));
    }

    @Test
    void getAverageMarkForSubject_ReturnsCorrectAverage_WhenWeHaveThreeGradeForSubject() {
        targetClass.addGrade(x);
        targetClass.addGrade(y);
        targetClass.addGrade(z);
        targetClass.addGrade(c);
        assertEquals(6.13, targetClass.getAverageMarkForSubject("Math"));
    }

    @Test
    void getAverageMarkForSubject_ReturnsException_WhenWeHaveNotGradeForSubject() {
        assertThrows(NoSuchElementException.class, () -> targetClass.getAverageMarkForSubject("Physics"));
    }

    @Test
    void getAverageMarkForSubject_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.getAverageMarkForSubject(null));
    }
}
