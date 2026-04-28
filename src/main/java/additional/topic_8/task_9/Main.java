package additional.topic_8.task_9;

import java.util.Collections;
import java.util.List;

public class Main {
    private static <T> void swap(List<T> list, int indexX, int indexY) {
        if (list == null || list.size() < 2) throw new  IllegalArgumentException("Illegal list");
        if (indexX < 0 || indexX >= list.size() || indexY < 0 || indexY >= list.size()) throw new IndexOutOfBoundsException("Index out of bound");
        Collections.swap(list, indexX, indexY);
    }
}
