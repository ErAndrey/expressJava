package additional.topic_8.task_14;

import java.util.Collection;

public class Main {
    private static <T extends Number> boolean argument(Collection<T> collection){
        if (collection == null) throw new IllegalArgumentException("Collection is null");
        for (T t : collection) {
            if (t.doubleValue() < 0) return false;
        }
        return true;
    }
}
