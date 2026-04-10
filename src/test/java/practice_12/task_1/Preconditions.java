package practice_12.task_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests \"EntityManager<T extends Entity>\"")
public class Preconditions {
    protected EntityManager<Developer> targetClass;

    protected Developer x;
    protected Developer y;
    protected Developer z;

    @BeforeEach
    void getPreconditions() {
        this.targetClass = new EntityManager<>();
        this.x = new Developer("Андрей", 25, true);
        this.y = new Developer("Данил", 20, false);
        this.z = new Developer("Саша", 30, false);
    }
}
