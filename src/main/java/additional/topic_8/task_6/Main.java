package additional.topic_8.task_6;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {
    private static <T> void printArray(List<T> list) {
        if (list.isEmpty()) throw new NoSuchElementException("List is empty");
        list.forEach(System.out::println);
    }
}
