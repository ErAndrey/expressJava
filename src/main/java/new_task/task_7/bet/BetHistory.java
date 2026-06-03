package new_task.task_7.bet;

import java.util.LinkedHashMap;
import java.util.Map;

public class BetHistory <K, V> extends LinkedHashMap<K, V> {
    private final int historySize;

    public BetHistory(int historySize) {
        super(historySize, 0.75f, true);
        this.historySize = historySize;
    }

    public int getHistorySize() { return this.historySize; }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > historySize;
    }
}
