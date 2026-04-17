package practice_12.task_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests \"UserValidator\"")
public class Preconditions {
    protected User user;
    protected UserValidator targetClass;

    @BeforeEach
    void getPreconditions() {
        this.user = new User("Андрей", 20, "ero@bk.ru");
        this.targetClass = new UserValidator();
        this.targetClass.updateValidationEnabled(true);
    }
}
