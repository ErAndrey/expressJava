package world_wars.ccpu;

import world_wars.State;
import world_wars.builds.attacks.Barracks;
import world_wars.builds.attacks.Technique;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;
import world_wars.general.Currency;
import world_wars.units.*;

import java.util.HashMap;
import java.util.Map;

public record CreateUnit(Currency price, Currency consume) {

    private final static Map<UnitType, CreateUnit> UNIT_TO_CREATE = new HashMap<>();
    private final static Map<Integer, Map<UnitType, Integer>> UNIT_FROM_BARRACKS = new HashMap<>();
    private final static Map<Integer, Map<UnitType, Integer>> UNIT_FROM_TECHNIQUE = new HashMap<>();

    public static Map<UnitType, Integer> getLimitToCreateFromBarracks(Barracks barracks) {
        return UNIT_FROM_BARRACKS.get(barracks.getLvl());
    }
    public static Map<UnitType, Integer> getLimitToCreateFromTechnique(Technique technique) {
        return UNIT_FROM_TECHNIQUE.get(technique.getLvl());
    }

    public static CreateUnit getCreateUnitInfo(UnitType type) {
        return UNIT_TO_CREATE.get(type);
    }
    public static void createUnit(State state, UnitType type) {
        Unit createdUnit = switch (type) {
            case SCOUT -> new Scout();
            case SOLDIER -> new Soldier();
            case SNIPER -> new Sniper();
            case TANK -> new Tank();
            case PLANE -> new Plane();
            case DRONE -> new Drone();
            case FLARE_GUN -> new FlareGun();
        };
        state.getCurrentBalance().withdrawCurrency(UNIT_TO_CREATE.get(createdUnit.getType()).price);
        state.addUnit(createdUnit);
    }

    static {

        UNIT_FROM_BARRACKS.put(1, new HashMap<>(
                Map.of(
                        UnitType.SCOUT, 2,
                        UnitType.SOLDIER, 1
                )
        ));

        UNIT_FROM_BARRACKS.put(2, new HashMap<>(
                Map.of(
                        UnitType.SCOUT, 3,
                        UnitType.SOLDIER, 2,
                        UnitType.SNIPER, 1
                )
        ));

        UNIT_FROM_BARRACKS.put(3, new HashMap<>(
                Map.of(
                        UnitType.SCOUT, 4,
                        UnitType.SOLDIER, 3,
                        UnitType.SNIPER, 2
                )
        ));

        UNIT_FROM_BARRACKS.put(4, new HashMap<>(
                Map.of(
                        UnitType.TANK, 6,
                        UnitType.SOLDIER, 4,
                        UnitType.SNIPER, 3
                )
        ));

        UNIT_FROM_TECHNIQUE.put(1, new HashMap<>(
                Map.of(UnitType.TANK, 1)
        ));

        UNIT_FROM_TECHNIQUE.put(2, new HashMap<>(
                Map.of(
                        UnitType.TANK, 2,
                        UnitType.PLANE, 1,
                        UnitType.DRONE, 1
                )
        ));

        UNIT_FROM_TECHNIQUE.put(3, new HashMap<>(
                Map.of(
                        UnitType.TANK, 3,
                        UnitType.PLANE, 2,
                        UnitType.DRONE, 3
                )
        ));

        UNIT_TO_CREATE.put(UnitType.SCOUT, new CreateUnit(
                Currency.of(10,0,0,0,0,0),
                Consume.getConsume(UnitType.SCOUT)
        ));

        UNIT_TO_CREATE.put(UnitType.SOLDIER, new CreateUnit(
                Currency.of(20,0,0,0,0,0),
                Consume.getConsume(UnitType.SOLDIER)
        ));

        UNIT_TO_CREATE.put(UnitType.SNIPER, new CreateUnit(
                Currency.of(30,0,0,10,0,0),
                Consume.getConsume(UnitType.SNIPER)
        ));

        UNIT_TO_CREATE.put(UnitType.TANK, new CreateUnit(
                Currency.of(30,0,10,0,15,0),
                Consume.getConsume(UnitType.TANK)
        ));

        UNIT_TO_CREATE.put(UnitType.PLANE, new CreateUnit(
                Currency.of(40,0,15,0,25,0),
                Consume.getConsume(UnitType.PLANE)
        ));

        UNIT_TO_CREATE.put(UnitType.DRONE, new CreateUnit(
                Currency.of(15,0,0,5,5,0),
                Consume.getConsume(UnitType.DRONE)
        ));

        UNIT_TO_CREATE.put(UnitType.FLARE_GUN, new CreateUnit(
                Currency.of(60,0,15,15,30,0),
                Consume.getConsume(UnitType.FLARE_GUN)
        ));
    }
}
