package practice_12.task_6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

@DisplayName("Preconditions for Tests \" TaskService <T>\"")
public class Preconditions {
    protected TaskService<Integer> targetClass;
    protected Task<Integer> x1;
    protected Task<Integer> x2;
    protected Task<Integer> x3;
    protected Task<Integer> x4;
    protected Task<Integer> x5;
    protected Task<Integer> x6;

    @BeforeEach
    void getPreconditions() {
        targetClass = new TaskService<>();
        x1 = new Task<>(1, Priority.LOW, LocalDate.of(2026, 4, 15));
        x2 = new Task<>(2, Priority.HIGH, LocalDate.of(2026, 3, 12));
        x3 = new Task<>(3, Priority.HIGH, LocalDate.of(2026, 4, 5));
        x4 = new Task<>(4, Priority.MEDIUM, LocalDate.of(2026, 1, 27));
        x5 = new Task<>(5, Priority.LOW, LocalDate.of(2026, 3, 30));
        x6 = new Task<>(6, Priority.BLOCKER, LocalDate.of(2026, 2, 11));
    }
}
