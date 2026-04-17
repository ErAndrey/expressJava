package practice_12.task_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests for : \"boolean checkEmail()\"")
public class CheckEmailTest extends Preconditions {

    /**
     * validationEnabled : false -> false
     *
     * validationEnabled : true ->
     * Positive :
     * "ru0@b0a.ru", "abcd@bk.com" -> true
     *
     * Corner
     * "avsa@ru","avsa.ru"  -> InvalidUserException
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    @Test
    void checkEmail_ReturnsFalse_WhenValidationEnabledFalseAndUserEmailIsValid() {
        targetClass.updateValidationEnabled(false);
        assertFalse(targetClass.checkEmail(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ru0@b0a.ru", "abcd@bk.com"})
    void checkName_ReturnsTrue_WhenNameIsCorrect(String email) {
        user.setEmail(email);
        assertTrue(targetClass.checkEmail(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"avsa@ru","avsa.ru", ""})
    void checkName_ReturnsException_WhenNameIsIncorrect(String email) {
        user.setEmail(email);
        assertThrows(InvalidUserException.class, () -> targetClass.checkEmail(user));
    }

    @Test
    void checkName_ReturnsException_WhenInputIsNull() {
        user.setEmail(null);
        assertThrows(IllegalArgumentException.class, () -> targetClass.checkEmail(user));
    }
}
