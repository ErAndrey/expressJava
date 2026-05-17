package new_task.task_7;

import java.util.Map;

public record Card(String rank, String suit) {
    private static final Map<String, Integer> VALUES = Map.ofEntries(
            Map.entry("2", 2),
            Map.entry("3", 3),
            Map.entry("4", 4),
            Map.entry("5", 5),
            Map.entry("6", 6),
            Map.entry("7", 7),
            Map.entry("8", 8),
            Map.entry("9", 9),
            Map.entry("10", 10),
            Map.entry("J", 10),
            Map.entry("Q", 10),
            Map.entry("K", 10),
            Map.entry("A", 11)
    );

    public int getValue() {
        return VALUES.get(rank);
    }

    public String toString() {
        String coloredSuit = suit.equals("♥") || suit.equals("♦") ? Utils.toError(suit) : suit;
        return rank + coloredSuit;
    }
}