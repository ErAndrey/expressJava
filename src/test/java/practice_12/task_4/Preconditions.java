package practice_12.task_4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests \"MovieService<T extends Number>\"")
public class Preconditions {
    protected MovieService<Integer> targetClass;
    protected Movie m1;
    protected Movie m2;
    protected Rating<Integer> r1;
    protected Rating<Integer> r3;
    protected Rating<Integer> r5;
    protected Rating<Integer> r8;
    protected Rating<Integer> r10;

    @BeforeEach
    void getPreconditions() {
        this.targetClass = new MovieService<>();
        this.m1 = new Movie("Harry Potter");
        this.m2 = new Movie("Still Alive");
        this.r1 = new Rating<>(1);
        this.r3 = new Rating<>(3);
        this.r5 = new Rating<>(5);
        this.r8 = new Rating<>(8);
        this.r10 = new Rating<>(10);
    }
}
