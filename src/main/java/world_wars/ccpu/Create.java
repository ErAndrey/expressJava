package world_wars.ccpu;

import world_wars.Currency;
import world_wars.State;

import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.entity.Unit;
import world_wars.entity.UnitType;

import world_wars.builds.attacks.*;
import world_wars.builds.defends.*;
import world_wars.builds.farmings.*;
import world_wars.builds.tradings.*;
import world_wars.units.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//toDo Разбить на CreateBuild и CreateUnit - чтоб для build иметь возможность показывать produce();
public record Create(int requiredCapitalLvl, Currency price) {

    private final static Map<BuildType, Create> BUILD_TO_CREATE = new HashMap<>();
    public static List<BuildType> getAvailableBuildToCreate(State state) {
        return BUILD_TO_CREATE.entrySet().stream()
                .filter(entry -> state.getCapital().getLvl() >= entry.getValue().requiredCapitalLvl)
                .map(Map.Entry::getKey)
                .toList();
    }
    public static void createBuild(State state, BuildType type) {
        Build createdBuild = switch (type) {
            case CAPITAL -> null;
            case FARM -> new Farm();
            case FACTORY -> new Factory();
            case SAWMILL -> new Sawmill();
            case MINE -> new Mine();
            case OIL_RIG -> new OilRig();
            case SHOP -> new Shop();
            case BARRACKS -> new Barracks();
            case TECHNIQUE -> new Technique();
            case TRADE -> new Trade();
            case TOWER_1 -> new Tower_1();
            case TOWER_2 -> new Tower_2();
            case TOWER_3 -> new Tower_3();
            case TOWER_4 -> new Tower_4();
        };
        state.getCurrentBalance().withdrawCurrency(BUILD_TO_CREATE.get(createdBuild.getType()).price);
        state.addBuild(createdBuild);
        state.getChangeBalance().depositCurrency(createdBuild.consume());
        state.getChangeBalance().depositCurrency(createdBuild.produce());
    }

    private final static Map<UnitType, Create> UNIT_TO_CREATE = new HashMap<>();
    public static List<UnitType> getAvailableUnitToCreate(State state) {
        return UNIT_TO_CREATE.entrySet().stream()
                .filter(entry -> state.getCapital().getLvl() >= entry.getValue().requiredCapitalLvl)
                .map(Map.Entry::getKey)
                .toList();
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
        state.getChangeBalance().depositCurrency(createdUnit.consume());
    }

    static {
        BUILD_TO_CREATE.put(BuildType.FARM, new Create(1, Currency.of(0, 0, 2, 2, 0, 0)));
        BUILD_TO_CREATE.put(BuildType.FACTORY, new Create(1, Currency.of(10, 0, 3, 0, 1, 0)));
        BUILD_TO_CREATE.put(BuildType.SAWMILL, new Create(1, Currency.of(10, 0, 0, 3, 1, 0)));
        BUILD_TO_CREATE.put(BuildType.MINE, new Create(1, Currency.of(0, 0, 2, 2, 3, 0)));
        BUILD_TO_CREATE.put(BuildType.OIL_RIG, new Create(2, Currency.of(30, 0, 8, 8, 4, 0)));

        BUILD_TO_CREATE.put(BuildType.SHOP, new Create(1, Currency.of(0, 0, 8, 8, 4, 0)));
        BUILD_TO_CREATE.put(BuildType.BARRACKS, new Create(1, Currency.of(0, 0, 4, 4, 2, 0)));
        BUILD_TO_CREATE.put(BuildType.TECHNIQUE, new Create(2, Currency.of(50, 0, 10, 10, 8, 0)));
        BUILD_TO_CREATE.put(BuildType.TRADE, new Create(2, Currency.of(50, 0, 0, 0, 15, 0)));

        BUILD_TO_CREATE.put(BuildType.TOWER_1, new Create(1, Currency.of(0, 0, 0, 7, 0, 0)));
        BUILD_TO_CREATE.put(BuildType.TOWER_2, new Create(2, Currency.of(10, 0, 0, 10, 5, 0)));
        BUILD_TO_CREATE.put(BuildType.TOWER_3, new Create(3, Currency.of(20, 0, 13, 0, 8, 0)));
        BUILD_TO_CREATE.put(BuildType.TOWER_4, new Create(4, Currency.of(40, 0, 20, 20, 15, 20)));

        UNIT_TO_CREATE.put(UnitType.SCOUT, new Create(1, Currency.of(10,0,0,0,0,0)));
        UNIT_TO_CREATE.put(UnitType.SOLDIER, new Create(1, Currency.of(20,0,0,0,0,0)));
        UNIT_TO_CREATE.put(UnitType.SNIPER, new Create(2, Currency.of(30,0,0,10,0,0)));

        UNIT_TO_CREATE.put(UnitType.TANK, new Create(2, Currency.of(30,0,10,0,15,0)));
        UNIT_TO_CREATE.put(UnitType.PLANE, new Create(3, Currency.of(40,0,15,0,25,0)));

        UNIT_TO_CREATE.put(UnitType.DRONE, new Create(3, Currency.of(15,0,0,5,5,0)));
        UNIT_TO_CREATE.put(UnitType.FLARE_GUN, new Create(4, Currency.of(60,0,15,15,30,0)));
    }
}
