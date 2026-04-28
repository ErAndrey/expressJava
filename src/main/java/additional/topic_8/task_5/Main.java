package additional.topic_8.task_5;

import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    private static <T extends Number> double getMinV1(List<T> list) {
        return list.stream()
                .mapToDouble(Number::doubleValue)
                .min()
                .orElseThrow(NoSuchElementException::new);
    }

    private static <T extends Number> double getMinV2(List<T> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException();
        double min = list.getFirst().doubleValue();
        for (T t : list) {
            if (t.doubleValue() < min) min = t.doubleValue();
        }
        return min;
    }
}
