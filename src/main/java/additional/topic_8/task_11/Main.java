package additional.topic_8.task_11;

import java.util.*;

public class Main {
    private static <K, V> V getValueFromMap(Map<K, V> map, K key) {
        if (map == null) throw new IllegalArgumentException("Map is null");
        if (map instanceof TreeMap && key == null) throw new IllegalArgumentException("Key is null");
        if (!map.containsKey(key)) throw new NoSuchElementException("Map no contains value for key");
        return map.get(key);
    }
}
