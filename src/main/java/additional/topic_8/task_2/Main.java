package additional.topic_8.task_2;

import java.util.Collection;

public class Main {
    private static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t.toString());
        }
    }
    private static <T> void print(Collection<T> list) {
        list.forEach(System.out::println);
    }
}
