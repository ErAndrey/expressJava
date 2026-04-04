package practice_10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests Homework")
abstract class Preconditions {
    protected Homework targetClass;

    @BeforeEach
    void getPreconditions() {
        this.targetClass = new Homework();
    }
}
