package world_wars.ccpu;

import world_wars.general.Currency;
import world_wars.entity.Build;
import world_wars.entity.BuildType;

import java.util.HashMap;
import java.util.Map;

public class Produce {
    private final static Map<BuildType, Map<Integer, Currency>> BUILD_PRODUCING = new HashMap<>();

    public static Currency getProduce(Build build) { return BUILD_PRODUCING.get(build.getType()).get(build.getLvl()); }
    public static Currency getProduceForLvl(BuildType type, int lvl) { return BUILD_PRODUCING.get(type).get(lvl); }

    static {
        BUILD_PRODUCING.put(BuildType.CAPITAL, new HashMap<>(
                Map.of(
                        1, Currency.of(2, 0, 0, 0, 1, 0),
                        2, Currency.of(4, 0, 0, 0, 2, 0),
                        3, Currency.of(8, 0, 0, 0, 4, 0),
                        4, Currency.of(16, 0, 0, 0, 8, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.FARM, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 3, 0, 0, 0, 0),
                        2, Currency.of(0, 5, 0, 0, 0, 0),
                        3, Currency.of(0, 8, 0, 0, 0, 0),
                        4, Currency.of(0, 13, 0, 0, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.SAWMILL, new HashMap<>(
                Map.of(
                        1, Currency.of(2, 0, 0, 1, 0, 0),
                        2, Currency.of(3, 0, 0, 3, 0, 0),
                        3, Currency.of(5, 0, 0, 7, 0, 0),
                        4, Currency.of(8, 0, 0, 12, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.FACTORY, new HashMap<>(
                Map.of(
                        1, Currency.of(2, 0, 1, 0, 0, 0),
                        2, Currency.of(3, 0, 3, 0, 0, 0),
                        3, Currency.of(5, 0, 7, 0, 0, 0),
                        4, Currency.of(8, 0, 12, 0, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.MINE, new HashMap<>(
                Map.of(
                        1, Currency.of(2, 0, 0, 0, 1, 0),
                        2, Currency.of(3, 0, 0, 0, 3, 0),
                        3, Currency.of(5, 0, 0, 0, 7, 0),
                        4, Currency.of(8, 0, 0, 0, 12, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.OIL_RIG, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 5),
                        2, Currency.of(0, 0, 0, 0, 0, 8),
                        3, Currency.of(0, 0, 0, 0, 0, 13)
                )
        ));

        BUILD_PRODUCING.put(BuildType.SHOP, new HashMap<>(
                Map.of(
                        1, Currency.of(2, 0, 1, 1, 0, 0),
                        2, Currency.of(4, 2, 2, 2, 1, 0),
                        3, Currency.of(6, 4, 4, 4, 2, 1),
                        4, Currency.of(9, 6, 6, 6, 4, 3)
                )
        ));

        BUILD_PRODUCING.put(BuildType.TRADE, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 0),
                        2, Currency.of(0, 0, 0, 0, 0, 0),
                        3, Currency.of(0, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.BARRACKS, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 0),
                        2, Currency.of(0, 0, 0, 0, 0, 0),
                        3, Currency.of(0, 0, 0, 0, 0, 0),
                        4, Currency.of(0, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.TECHNIQUE, new HashMap<>(
                Map.of(
                        1, Currency.of(4, 0, 0, 0, 0, 0),
                        2, Currency.of(8, 0, 0, 0, 0, 0),
                        3, Currency.of(16, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_PRODUCING.put(BuildType.TOWER_1, new HashMap<>(
                Map.of(1, Currency.of(0, 0, 0, 0, 0, 0))
        ));

        BUILD_PRODUCING.put(BuildType.TOWER_2, new HashMap<>(
                Map.of(2, Currency.of(0, 0, 0, 0, 0, 0))
        ));

        BUILD_PRODUCING.put(BuildType.TOWER_3, new HashMap<>(
                Map.of(3, Currency.of(0, 0, 0, 0, 0, 0))
        ));

        BUILD_PRODUCING.put(BuildType.TOWER_4, new HashMap<>(
                Map.of(4, Currency.of(0, 0, 0, 0, 0, 0))
        ));
    }
}
