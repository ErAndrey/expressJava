package world_wars.ccpu;

import world_wars.State;
import world_wars.builds.attacks.Barracks;
import world_wars.builds.attacks.Technique;
import world_wars.builds.defends.Tower;
import world_wars.builds.farmings.*;
import world_wars.builds.tradings.Shop;
import world_wars.builds.tradings.Trade;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.general.Currency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CreateBuild(int requiredCapitalLvl, Currency price, Currency consume, Currency produce) {
    private final static Map<BuildType, CreateBuild> BUILD_TO_CREATE = new HashMap<>();

    public static List<BuildType> getAvailableBuildsToCreate(State state) {
        return BUILD_TO_CREATE.entrySet().stream()
                .filter(entry -> state.getCapital().getLvl() >= entry.getValue().requiredCapitalLvl)
                .map(Map.Entry::getKey)
                .toList();
    }
    public static CreateBuild getCreateBuildInfo(BuildType type) {
        return BUILD_TO_CREATE.get(type);
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
            case TOWER_1 -> new Tower(1);
            case TOWER_2 -> new Tower(2);
            case TOWER_3 -> new Tower(3);
            case TOWER_4 -> new Tower(4);
        };
        state.getCurrentBalance().withdrawCurrency(BUILD_TO_CREATE.get(createdBuild.getType()).price);
        state.addBuild(createdBuild);
    }

    static {
        BUILD_TO_CREATE.put(BuildType.FARM, new CreateBuild(1,
                Currency.of(0, 0, 2, 2, 0, 0),
                Consume.getConsumeForLvl(BuildType.FARM, 1),
                Produce.getProduceForLvl(BuildType.FARM, 1))
        );

        BUILD_TO_CREATE.put(BuildType.FACTORY, new CreateBuild(1,
                Currency.of(10, 0, 3, 0, 1, 0),
                Consume.getConsumeForLvl(BuildType.FACTORY, 1),
                Produce.getProduceForLvl(BuildType.FACTORY, 1))
        );

        BUILD_TO_CREATE.put(BuildType.SAWMILL, new CreateBuild(1,
                Currency.of(10, 0, 0, 3, 1, 0),
                Consume.getConsumeForLvl(BuildType.SAWMILL, 1),
                Produce.getProduceForLvl(BuildType.SAWMILL, 1))
        );

        BUILD_TO_CREATE.put(BuildType.MINE, new CreateBuild(1,
                Currency.of(0, 0, 2, 2, 3, 0),
                Consume.getConsumeForLvl(BuildType.MINE, 1),
                Produce.getProduceForLvl(BuildType.MINE, 1))
        );

        BUILD_TO_CREATE.put(BuildType.OIL_RIG, new CreateBuild(2,
                Currency.of(30, 0, 8, 8, 4, 0),
                Consume.getConsumeForLvl(BuildType.OIL_RIG, 1),
                Produce.getProduceForLvl(BuildType.OIL_RIG, 1))
        );

        BUILD_TO_CREATE.put(BuildType.SHOP, new CreateBuild(1,
                Currency.of(0, 0, 8, 8, 4, 0),
                Consume.getConsumeForLvl(BuildType.SHOP, 1),
                Produce.getProduceForLvl(BuildType.SHOP, 1))
        );

        BUILD_TO_CREATE.put(BuildType.TRADE, new CreateBuild(2,
                Currency.of(50, 0, 0, 0, 15, 0),
                Consume.getConsumeForLvl(BuildType.TRADE, 1),
                Produce.getProduceForLvl(BuildType.TRADE, 1))
        );

        BUILD_TO_CREATE.put(BuildType.BARRACKS, new CreateBuild(1,
                Currency.of(0, 0, 4, 4, 2, 0),
                Consume.getConsumeForLvl(BuildType.BARRACKS, 1),
                Produce.getProduceForLvl(BuildType.BARRACKS, 1))
        );

        BUILD_TO_CREATE.put(BuildType.TECHNIQUE, new CreateBuild(2,
                Currency.of(50, 0, 10, 10, 8, 0),
                Consume.getConsumeForLvl(BuildType.TECHNIQUE, 1),
                Produce.getProduceForLvl(BuildType.TECHNIQUE, 1))
        );

        BUILD_TO_CREATE.put(BuildType.TOWER_1, new CreateBuild(1,
                Currency.of(0, 0, 0, 7, 0, 0),
                Consume.getConsumeForLvl(BuildType.TOWER_1, 1),
                Produce.getProduceForLvl(BuildType.TOWER_1, 1))
        );

        BUILD_TO_CREATE.put(BuildType.TOWER_2, new CreateBuild(2,
                Currency.of(10, 0, 0, 10, 5, 0),
                Consume.getConsumeForLvl(BuildType.TOWER_2, 2),
                Produce.getProduceForLvl(BuildType.TOWER_2, 2))
        );

        BUILD_TO_CREATE.put(BuildType.TOWER_3, new CreateBuild(3,
                Currency.of(20, 0, 13, 0, 8, 0),
                Consume.getConsumeForLvl(BuildType.TOWER_3, 3),
                Produce.getProduceForLvl(BuildType.TOWER_3, 3))
        );

        BUILD_TO_CREATE.put(BuildType.TOWER_4, new CreateBuild(4,
                Currency.of(40, 0, 20, 20, 15, 20),
                Consume.getConsumeForLvl(BuildType.TOWER_4, 4),
                Produce.getProduceForLvl(BuildType.TOWER_4, 4))
        );
    }
}
