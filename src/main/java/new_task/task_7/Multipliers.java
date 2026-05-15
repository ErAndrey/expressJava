package new_task.task_7;

import java.util.Map;
import java.util.stream.Collectors;

public class Multipliers {
    public static final double REDUCE_MULTIPLIER_FOR_SLOTS_GAME = 0.7;
    private static final Map<String, Double> HONEST_SLOTS_MULTIPLIERS = Map.ofEntries(
            // 5 слотов
            Map.entry("5_5s", 10000.0),
            Map.entry("5_4r+2s+2s", 1111.0),
            Map.entry("5_4s", 556.0),
            Map.entry("5_3s+2s", 556.0),
            Map.entry("5_3r+2r", 556.0),
            Map.entry("5_4r+3s", 556.0),
            Map.entry("5_2r+3s", 556.0),
            Map.entry("5_3r+2s+2s", 556.0),
            Map.entry("5_3s", 46.3),
            Map.entry("5_2s+2s", 46.3),
            Map.entry("5_3r+2s", 27.8),
            Map.entry("5_2r+2r", 23.1),
            Map.entry("5_2r+2s", 23.1),
            Map.entry("5_3r", 19.8),
            Map.entry("5_2s", 4.96),
            Map.entry("5_2r", 3.31),

            // 4 слота
            Map.entry("4_4s", 1000.0),
            Map.entry("4_2r+2r", 111.0),
            Map.entry("4_2r+2s", 111.0),
            Map.entry("4_3r+2s", 55.6),
            Map.entry("4_2s+2s", 55.6),
            Map.entry("4_3s", 55.6),
            Map.entry("4_2s", 4.63),
            Map.entry("4_2r", 4.63),

            // 3 слота
            Map.entry("3_3s", 100.0),
            Map.entry("3_2r", 11.1),
            Map.entry("3_2s", 5.56),

            // 2 слота
            Map.entry("2_2s", 10.0)
    );

    public static final Map<String, Double> SLOTS_MULTIPLIERS;

    static {
        SLOTS_MULTIPLIERS = getSlotsMultipliers();
    }

    private static Map<String, Double> getSlotsMultipliers() {
        return HONEST_SLOTS_MULTIPLIERS.entrySet().stream().
                collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Utils.getClearMultiplier(entry.getValue() * REDUCE_MULTIPLIER_FOR_SLOTS_GAME)
                ));
    }
}
