package additional.topic_8.task_12;

import java.util.List;

public class Main {
    private static <T> List<T> mapArrayToList(T[] array) {
        if (array == null) throw new IllegalArgumentException("Array is null");
        return List.of(array);
    }
}
