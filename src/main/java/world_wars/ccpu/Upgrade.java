package world_wars.ccpu;

import world_wars.builds.Capital;
import world_wars.builds.attacks.Technique;
import world_wars.builds.farmings.OilRig;
import world_wars.builds.tradings.Trade;
import world_wars.general.Currency;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.State;

import java.util.*;

public record Upgrade(int toLvl, int requiredCapitalLvl, Currency price, Currency newProduce, Currency newConsume) {
    private final static Map<BuildType, HashMap<Integer, Upgrade>> TO_UPGRADE = new HashMap<>();

    public static Upgrade getUpgradeInfo(Build build) {
        return TO_UPGRADE.get(build.getType()).get(build.getLvl() + 1);
    }

    public static boolean haveLvlForUpgrade(State state, Build build) {
        if (build instanceof Capital) return true;
        return build instanceof OilRig || build instanceof Technique || build instanceof Trade ?
                state.getCapital().getLvl() >= build.getLvl() + 2 :
                state.getCapital().getLvl() >= build.getLvl() + 1;
    }

    public static boolean haveCurrencyForUpgrade(State state, Build build) {
        return state.isHaveBalanceToSpend(getUpgradeInfo(build).price);
    }



    public static void upgradeBuild(State state, Build build) {
        state.getCurrentBalance().withdrawCurrency(getUpgradeInfo(build).price);
        state.upgradeBuild(build);
    }

    static {
        TO_UPGRADE.put(BuildType.CAPITAL, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 1,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 2),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 2)),
                        3, new Upgrade(3, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 3),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 3)),
                        4, new Upgrade(4, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 4),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.FARM, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FARM, 2),
                                Consume.getConsumeForLvl(BuildType.FARM, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FARM, 3),
                                Consume.getConsumeForLvl(BuildType.FARM, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FARM, 4),
                                Consume.getConsumeForLvl(BuildType.FARM, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.FACTORY, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FACTORY, 2),
                                Consume.getConsumeForLvl(BuildType.FACTORY, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FACTORY, 3),
                                Consume.getConsumeForLvl(BuildType.FACTORY, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.FACTORY, 4),
                                Consume.getConsumeForLvl(BuildType.FACTORY, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.SAWMILL, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SAWMILL, 2),
                                Consume.getConsumeForLvl(BuildType.SAWMILL, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SAWMILL, 3),
                                Consume.getConsumeForLvl(BuildType.SAWMILL, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SAWMILL, 4),
                                Consume.getConsumeForLvl(BuildType.SAWMILL, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.MINE, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.MINE, 2),
                                Consume.getConsumeForLvl(BuildType.MINE, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.MINE, 3),
                                Consume.getConsumeForLvl(BuildType.MINE, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.MINE, 4),
                                Consume.getConsumeForLvl(BuildType.MINE, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.OIL_RIG, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 3,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.OIL_RIG, 2),
                                Consume.getConsumeForLvl(BuildType.OIL_RIG, 2)),
                        3, new Upgrade(3, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.OIL_RIG, 3),
                                Consume.getConsumeForLvl(BuildType.OIL_RIG, 3))
                )
        ));

        TO_UPGRADE.put(BuildType.SHOP, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SHOP, 2),
                                Consume.getConsumeForLvl(BuildType.SHOP, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SHOP, 3),
                                Consume.getConsumeForLvl(BuildType.SHOP, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.SHOP, 4),
                                Consume.getConsumeForLvl(BuildType.SHOP, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.TRADE, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 3,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TRADE, 2),
                                Consume.getConsumeForLvl(BuildType.TRADE, 2)),
                        3, new Upgrade(3, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TRADE, 3),
                                Consume.getConsumeForLvl(BuildType.TRADE, 3))
                )
        ));

        TO_UPGRADE.put(BuildType.BARRACKS, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.BARRACKS, 2),
                                Consume.getConsumeForLvl(BuildType.BARRACKS, 2)),
                        3, new Upgrade(3, 3,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.BARRACKS, 3),
                                Consume.getConsumeForLvl(BuildType.BARRACKS, 3)),
                        4, new Upgrade(4, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.BARRACKS, 4),
                                Consume.getConsumeForLvl(BuildType.BARRACKS, 4))
                )
        ));

        TO_UPGRADE.put(BuildType.TECHNIQUE, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 3,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TECHNIQUE, 2),
                                Consume.getConsumeForLvl(BuildType.TECHNIQUE, 2)),
                        3, new Upgrade(3, 4,
                                Currency.of(1,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TECHNIQUE, 3),
                                Consume.getConsumeForLvl(BuildType.TECHNIQUE, 3))
                )
        ));

        TO_UPGRADE.put(BuildType.TOWER_1, new HashMap<>(
                Map.of(
                        2, new Upgrade(2, 2,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TOWER_2, 2),
                                Consume.getConsumeForLvl(BuildType.TOWER_2, 2))
                )
        ));

        TO_UPGRADE.put(BuildType.TOWER_2, new HashMap<>(
                Map.of(
                        3, new Upgrade(3, 3,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TOWER_3, 3),
                                Consume.getConsumeForLvl(BuildType.TOWER_3, 3))
                )
        ));

        TO_UPGRADE.put(BuildType.TOWER_3, new HashMap<>(
                Map.of(
                        4, new Upgrade(4, 4,
                                Currency.of(15,1,1,1,1,0),
                                Produce.getProduceForLvl(BuildType.TOWER_4, 4),
                                Consume.getConsumeForLvl(BuildType.TOWER_4, 4))
                )
        ));
    }
}
