package practice_10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests for : \"boolean isValidJson(String json)\"")
public final class IsValidJsonTest extends Preconditions {
    @ParameterizedTest
    @ValueSource(strings = {
            "{}",
            "{\"key\":\"value\"}",
            "{\"key\":123}",
            "{\"key\":true}",
            "{\"key\":null}",
            "{\"key\":[1,2,3]}",
    })
    void isValidJson_ReturnsTrue_WhenJsonIsValid(String json) {
        assertTrue(targetClass.isValidJson(json));
    }

    //toDo Json считается корректным, если он пустой?
    @ParameterizedTest
    @ValueSource(strings = {" ", "   ",})
    void isValidJson_ReturnsFalse_WhenJsonIsEmpty(String json) {
        assertFalse(targetClass.isValidJson(json));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{key: \"value\"}",
            "{'key': 'value'}",
            "{\"key\": value}",
            "{\"key\":}",
            "{,}",
            "{\"key\"\"value\"}",
            "{\"key\":\"value\"",
            "[1,2,3,]",
            "{\"key\":,}"
    })
    void isValidJson_ReturnsFalse_WhenJsonIsInvalid(String json) {
        assertFalse(targetClass.isValidJson(json));
    }
}
