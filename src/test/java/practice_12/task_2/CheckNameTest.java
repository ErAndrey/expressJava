package practice_12.task_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"boolean checkName()\"")
public class CheckNameTest extends Preconditions {

    /**
     * validationEnabled : false -> false
     *
     * validationEnabled : true ->
     * Positive :
     * "Andrey", "MashA", "Sasha" -> true
     *
     * Corner
     * "" -> InvalidUserException
     * "andrew" -> InvalidUserException
     *
     * Negative :
     * null -> IllegalArgumentException
     */

    @Test
    void checkName_ReturnsFalse_WhenValidationEnabledFalseAndUserNameIsValid() {
        targetClass.updateValidationEnabled(false);
        assertFalse(targetClass.checkName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Andrey", "MashA", "Sasha"})
    void checkName_ReturnsTrue_WhenNameIsCorrect(String name) {
        user.setName(name);
        assertTrue(targetClass.checkName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "andrew"})
    void checkName_ReturnsException_WhenNameIsIncorrect(String name) {
        user.setName(name);
        assertThrows(InvalidUserException.class, () -> targetClass.checkName());
    }

    @Test
    void checkName_ReturnsException_WhenInputIsNull() {
        user.setName(null);
        assertThrows(IllegalArgumentException.class, () -> targetClass.checkName());
    }
}
