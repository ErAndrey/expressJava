package world_wars.ccpu;

import world_wars.Currency;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.State;
import world_wars.interfaces.Upgradable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public record Upgrade(int requiredCapitalLvl, Currency price, Currency newProduce, Currency newConsume) {

    private final static Map<BuildType, TreeMap<Integer, Upgrade>> TO_UPGRADE = new HashMap<>();
    public static Map<BuildType, TreeMap<Integer, Upgrade>> getToUpgrade() { return TO_UPGRADE; }

    public static boolean isCanBeUpgrade(State state, Build build) {
        return TO_UPGRADE.get(build.getType()).entrySet().stream()
                    .anyMatch(entry -> state.getCapital().getLvl() == 4 ?
                            entry.getKey() >= state.getCapital().getLvl() : entry.getKey() > state.getCapital().getLvl());

    }

    public static Upgrade getUpgrade(Build build) {
        return TO_UPGRADE.get(build.getType()).get(build.getLvl() + 1);
    }

    public static void upgrade(State state, Build build) {
        if (build instanceof Upgradable) {
            state.getCurrentBalance().withdrawCurrency(getUpgrade(build).price);
            state.getChangeBalance().withdrawCurrency(build.consume());
            state.getChangeBalance().withdrawCurrency(build.produce());
            ((Upgradable) build).upgrade();
            state.getChangeBalance().depositCurrency(build.consume());
            state.getChangeBalance().depositCurrency(build.produce());
        } else {
            throw new IllegalStateException("Здание" + build.getType() + " " + build.getLvl() + " уровня - не может быть улучшено");
        }
    }

    static {
        TO_UPGRADE.put(BuildType.CAPITAL, new TreeMap<>(
                Map.of(
                        2, new Upgrade(1,
                                Currency.of(1,1,1,1,1,1),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 2),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 2)),
                        3, new Upgrade(2,
                                Currency.of(1,1,1,1,1,1),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 3),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 3)),
                        4, new Upgrade(3,
                                Currency.of(1,1,1,1,1,1),
                                Produce.getProduceForLvl(BuildType.CAPITAL, 4),
                                Consume.getConsumeForLvl(BuildType.CAPITAL, 4))
                )
        ));
    }
}
