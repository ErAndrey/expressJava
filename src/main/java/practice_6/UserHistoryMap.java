package practice_6;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserHistoryMap<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public UserHistoryMap(int maxSize) {
        super(maxSize, 0.5f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}
