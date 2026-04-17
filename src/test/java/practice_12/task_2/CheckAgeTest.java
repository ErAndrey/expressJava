package practice_12.task_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"boolean checkAge()\"")
public class CheckAgeTest extends Preconditions {

    /**
     * validationEnabled : false -> false
     *
     * validationEnabled : true ->
     * Positive :
     * [18, 100] -> true
     *
     * Corner
     * [x, 17] -> InvalidUserException
     * [101, y] -> InvalidUserException
     */

    @Test
    void checkAge_ReturnsFalse_WhenValidationEnabledFalseAndUserAgeIsValid() {
        targetClass.updateValidationEnabled(false);
        assertFalse(targetClass.checkAge(user));
    }

    @ParameterizedTest
    @ValueSource(ints = {18, 25, 50, 100})
    void checkAge_ReturnsTrue_WhenAgeIsCorrect(int age) {
        user.setAge(age);
        assertTrue(targetClass.checkAge(user));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 17, 101, 200})
    void checkName_ReturnsException_WhenNameIsIncorrect(int age) {
        user.setAge(age);
        assertThrows(InvalidUserException.class, () -> targetClass.checkAge(user));
    }
}
