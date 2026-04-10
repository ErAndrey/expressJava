package practice_12.task_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests \"GradeService <T extends Number>\"")
public class Preconditions {
    protected GradeService<Double> targetClass;

    @BeforeEach
    void getPreconditions() {
        this.targetClass = new GradeService<>();
    }
}
