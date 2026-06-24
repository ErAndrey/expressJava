package world_wars.ccpu;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

import java.util.HashMap;
import java.util.Map;

public class Consume {
    private final static Map<BuildType, Map<Integer, Currency>> BUILD_CONSUMING = new HashMap<>();
    public static Currency getConsume(Build build) { return BUILD_CONSUMING.get(build.getType()).get(build.getLvl()); }
    public static Currency getConsumeForLvl(BuildType type, int lvl) { return BUILD_CONSUMING.get(type).get(lvl); }

    private final static Map<UnitType, Currency> UNIT_CONSUMING = new HashMap<>();
    public static Currency getConsume(Unit unit) { return UNIT_CONSUMING.get(unit.getType()); }

    public static Map<BuildType, Map<Integer, Currency>> getBuildConsuming() { return BUILD_CONSUMING; }
    public static Map<UnitType, Currency> getUnitConsuming() { return UNIT_CONSUMING; }

    static {
        // Capital, Shop, Trade -> Override
        BUILD_CONSUMING.put(BuildType.CAPITAL, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 0),
                        2, Currency.of(0, 0, 0, 0, 0, 0),
                        3, Currency.of(0, 0, 0, 0, 0, 0),
                        4, Currency.of(0, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.FARM, new HashMap<>(
                Map.of(
                        1, Currency.of(3, 0, 0, 0, 0, 0),
                        2, Currency.of(6, 0, 0, 0, 0, 0),
                        3, Currency.of(10, 0, 0, 0, 0, 0),
                        4, Currency.of(15, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.SAWMILL, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 2, 0, 0, 0, 0),
                        2, Currency.of(0, 3, 0, 0, 0, 0),
                        3, Currency.of(0, 5, 0, 0, 0, 0),
                        4, Currency.of(0, 8, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.FACTORY, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 2, 0, 0, 0, 0),
                        2, Currency.of(0, 3, 0, 0, 0, 0),
                        3, Currency.of(0, 5, 0, 0, 0, 0),
                        4, Currency.of(0, 8, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.MINE, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 3, 0, 0, 0, 0),
                        2, Currency.of(0, 5, 0, 0, 0, 0),
                        3, Currency.of(0, 8, 0, 0, 0, 0),
                        4, Currency.of(0, 13, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.OIL_RIG, new HashMap<>(
                Map.of(
                        1, Currency.of(5, 0, 0, 0, 3, 0),
                        2, Currency.of(8, 0, 0, 0, 6, 0),
                        3, Currency.of(13, 0, 0, 0, 10, 0),
                        4, Currency.of(21, 0, 0, 0, 15, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.SHOP, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 0),
                        2, Currency.of(0, 0, 0, 0, 0, 0),
                        3, Currency.of(0, 0, 0, 0, 0, 0),
                        4, Currency.of(0, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.TRADE, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 0, 0, 0, 0),
                        2, Currency.of(0, 0, 0, 0, 0, 0),
                        3, Currency.of(0, 0, 0, 0, 0, 0),
                        4, Currency.of(0, 0, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.BARRACKS, new HashMap<>(
                Map.of(
                        1, Currency.of(5, 3, 0, 0, 0, 0),
                        2, Currency.of(8, 5, 0, 0, 0, 0),
                        3, Currency.of(13, 8, 0, 0, 0, 0),
                        4, Currency.of(21, 13, 0, 0, 0, 0)
                )
        ));

        BUILD_CONSUMING.put(BuildType.TECHNIQUE, new HashMap<>(
                Map.of(
                        1, Currency.of(0, 0, 8, 8, 8, 8),
                        2, Currency.of(0, 0, 13, 13, 13, 13),
                        3, Currency.of(0, 0, 21, 21, 21, 21)
                )
        ));

        BUILD_CONSUMING.put(BuildType.TOWER_1, new HashMap<>(
                Map.of(1, Currency.of(3, 0, 0, 0, 0, 0))
        ));

        BUILD_CONSUMING.put(BuildType.TOWER_2, new HashMap<>(
                Map.of(2, Currency.of(8, 0, 0, 0, 0, 0))
        ));

        BUILD_CONSUMING.put(BuildType.TOWER_3, new HashMap<>(
                Map.of(3, Currency.of(21, 0, 0, 0, 8, 0))
        ));

        BUILD_CONSUMING.put(BuildType.TOWER_4, new HashMap<>(
                Map.of(4, Currency.of(55, 0, 0, 0, 13, 13))
        ));

        UNIT_CONSUMING.put(UnitType.SCOUT, Currency.of(3, 3, 0, 0, 0, 0));
        UNIT_CONSUMING.put(UnitType.SOLDIER, Currency.of(9, 3, 0, 0, 2, 0));
        UNIT_CONSUMING.put(UnitType.SNIPER, Currency.of(15, 3, 0, 6, 3, 0));
        UNIT_CONSUMING.put(UnitType.TANK, Currency.of(18, 6, 0, 0, 6, 18));
        UNIT_CONSUMING.put(UnitType.PLANE, Currency.of(36, 9, 0, 0, 12, 24));
        UNIT_CONSUMING.put(UnitType.DRONE, Currency.of(5, 0, 0, 0, 0, 5));
        UNIT_CONSUMING.put(UnitType.FLARE_GUN, Currency.of(45, 0, 15, 15, 0, 30));
    }
}
