package world_wars.general;

import world_wars.State;
import world_wars.diplomacy.RelationType;
import world_wars.entity.Build;
import world_wars.entity.Unit;
import world_wars.trading.TradingRequest;
import world_wars.trading.TradingType;

public class ToString {
    private static final String ID = "🆔";
    private static final String DEL = Utils.toPurple("|");
    private static final String OPEN = Utils.toPurple("[");
    private static final String CLOSE = Utils.toPurple("]");

    public static String forBuild(Build build) {
        StringBuilder sb = new StringBuilder();
        Currency consume = build.getConsume();
        Currency produce = build.getProduce();

        sb.append(ID).append(build.getId()).append(build.getType().toString()).append("#").append(build.getLvl()).append(" : ");

        sb.append(Utils.toYellow("["));
        sb.append(build.getDefence()).append(Icon.DEFENCE);
        sb.append(Utils.toYellow("]"));

        if (consume.isEmptyCurrency() && produce.isEmptyCurrency()) return sb.toString();

        if (!produce.isEmptyCurrency()) {
            sb.append(" & ").append(Utils.toGreen("["));
            if (produce.getGold() > 0) sb.append(produce.getGold()).append(Icon.GOLD);
            if (produce.getFood() > 0) sb.append(produce.getFood()).append(Icon.FOOD);
            if (produce.getStone() > 0) sb.append(produce.getStone()).append(Icon.STONE);
            if (produce.getTree() > 0) sb.append(produce.getTree()).append(Icon.TREE);
            if (produce.getOre() > 0) sb.append(produce.getOre()).append(Icon.ORE);
            if (produce.getOil() > 0) sb.append(produce.getOil()).append(Icon.OIL);
            sb.append(Utils.toGreen("]"));
        }

        if (!consume.isEmptyCurrency()) {
            sb.append(" & ").append(Utils.toRed("["));
            if (consume.getGold() > 0) sb.append(consume.getGold()).append(Icon.GOLD);
            if (consume.getFood() > 0) sb.append(consume.getFood()).append(Icon.FOOD);
            if (consume.getStone() > 0) sb.append(consume.getStone()).append(Icon.STONE);
            if (consume.getTree() > 0) sb.append(consume.getTree()).append(Icon.TREE);
            if (consume.getOil() > 0) sb.append(consume.getOre()).append(Icon.ORE);
            if (consume.getOil() > 0) sb.append(consume.getOil()).append(Icon.OIL);
            sb.append(Utils.toRed("]"));
        }

        return sb.toString();
    }

    public static String forUnit(Unit unit) {
        StringBuilder sb = new StringBuilder();
        Currency consume = unit.getConsume();

        sb.append(unit.getType().toString()).append(" : ");

        sb.append(Utils.toYellow("["));
        sb.append(unit.getPower()).append(Icon.ATTACK_POWER).append(" ");
        sb.append(unit.getDefence()).append(Icon.DEFENCE).append(" ");
        sb.append(unit.getAttackRadius()).append(Icon.ATTACK_RADIUS).append(" ");
        sb.append(unit.getMoveRadius()).append(Icon.MOVE_RADIUS);
        sb.append(Utils.toYellow("]"));

        if (consume.isEmptyCurrency()) return sb.toString();

        sb.append(" & ");

        sb.append(Utils.toRed("["));
        if (consume.getGold() > 0) sb.append(consume.getGold()).append(Icon.GOLD);
        if (consume.getFood() > 0) sb.append(consume.getFood()).append(Icon.FOOD);
        if (consume.getStone() > 0) sb.append(consume.getStone()).append(Icon.STONE);
        if (consume.getTree() > 0) sb.append(consume.getTree()).append(Icon.TREE);
        if (consume.getOre() > 0) sb.append(consume.getOre()).append(Icon.ORE);
        if (consume.getOil() > 0) sb.append(consume.getOil()).append(Icon.OIL);
        sb.append(Utils.toRed("]"));

        return sb.toString();
    }

    public static String forState(State state) {
        StringBuilder sb = new StringBuilder();

        sb.append(Icon.CAPITAL).append("#").append(state.getId()).append(" : ");
        sb.append(Icon.TECH).append("#").append(state.getCapital().getLvl()).append(" ");
        sb.append(Icon.BUILD).append("x").append(state.getBuildsSize()).append(" ");
        sb.append(Icon.UNIT).append("x").append(state.getUnitsSize());

        return sb.toString();
    }

    public static String forStateBalance(State state) {
        StringBuilder sb = new StringBuilder();
        Currency currentBalance = state.getCurrentBalance();
        Currency changeBalance = state.getChangeBalance();

        sb.append(Utils.toPurple("["));
        sb.append(Icon.GOLD).append(currentBalance.getGold()).append(selectColorForCurrencyWithPlus(changeBalance.getGold())).append(DEL);
        sb.append(Icon.FOOD).append(currentBalance.getFood()).append(selectColorForCurrencyWithPlus(changeBalance.getFood())).append(DEL);
        sb.append(Icon.STONE).append(currentBalance.getStone()).append(selectColorForCurrencyWithPlus(changeBalance.getStone())).append(DEL);
        sb.append(Icon.TREE).append(currentBalance.getTree()).append(selectColorForCurrencyWithPlus(changeBalance.getTree())).append(DEL);
        sb.append(Icon.ORE).append(currentBalance.getOre()).append(selectColorForCurrencyWithPlus(changeBalance.getOre())).append(DEL);
        sb.append(Icon.OIL).append(currentBalance.getOil()).append(selectColorForCurrencyWithPlus(changeBalance.getOil()));
        sb.append(Utils.toPurple("]"));

        return sb.toString();
    }

    public static String forCurrency(Currency currency, boolean isProduce) {
        if (currency.isEmptyCurrency()) return "[]";
        StringBuilder sb = new StringBuilder(OPEN);
        if (currency.getGold() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getGold()));
            } else {
                sb.append(Utils.toRed(currency.getGold()));
            }
            sb.append(Icon.GOLD);
            if (currency.getFood() > 0 || currency.getStone() > 0 || currency.getTree() > 0 || currency.getOre() > 0 || currency.getOil() > 0) sb.append(DEL);
        }
        if (currency.getFood() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getFood()));
            } else {
                sb.append(Utils.toRed(currency.getFood()));
            }
            sb.append(Icon.FOOD);
            if (currency.getStone() > 0 || currency.getTree() > 0 || currency.getOre() > 0 || currency.getOil() > 0) sb.append(DEL);
        }
        if (currency.getStone() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getStone()));
            } else {
                sb.append(Utils.toRed(currency.getStone()));
            }
            sb.append(Icon.STONE);
            if (currency.getTree() > 0 || currency.getOre() > 0 || currency.getOil() > 0) sb.append(DEL);
        }
        if (currency.getTree() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getTree()));
            } else {
                sb.append(Utils.toRed(currency.getTree()));
            }
            sb.append(Icon.TREE);
            if (currency.getOre() > 0 || currency.getOil() > 0) sb.append(DEL);
        }
        if (currency.getOre() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getOre()));
            } else {
                sb.append(Utils.toRed(currency.getOre()));
            }
            sb.append(Icon.ORE);
            if (currency.getOil() > 0) sb.append(DEL);
        }
        if (currency.getOil() > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen(currency.getOil()));
            } else {
                sb.append(Utils.toRed(currency.getOil()));
            }
            sb.append(Icon.OIL);
        }
        return sb.append(CLOSE).toString();
    }

    private static String selectColorForCurrencyWithPlus(int currency) {
        if (currency > 0) return "(" + Utils.toGreen("+" + currency) + ")";
        if (currency < 0) return "(" + Utils.toRed(currency) + ")";
        return "";
    }

    private static String selectColorForCurrencyNumber(int currency) {
        if (currency > 0) return Utils.toGreen(currency);
        if (currency < 0) return Utils.toRed(currency);
        return Utils.toYellow(currency);
    }

    public static String havePriceToSpend(Currency need, Currency have) {
        StringBuilder sb = new StringBuilder("[");

        boolean needGold = need.getGold() != 0;
        boolean needFood = need.getFood() != 0;
        boolean needStone = need.getStone() != 0;
        boolean needTree = need.getTree() != 0;
        boolean needOre = need.getOre() != 0;
        boolean needOil = need.getOil() != 0;

        if (needGold) {
            if (need.getGold() > have.getGold()) {
                sb.append(Utils.toRed(need.getGold()));
            } else {
                sb.append(need.getGold());
            }
            sb.append(Icon.GOLD);
            if (needFood || needStone || needTree || needOre || needOil) sb.append("|");
        }

        if (needFood) {
            if (need.getFood() > have.getFood()) {
                sb.append(Utils.toRed(need.getFood()));
            } else {
                sb.append(need.getFood());
            }
            sb.append(Icon.FOOD);
            if (needStone || needTree || needOre || needOil) sb.append("|");
        }

        if (needStone) {
            if (need.getStone() > have.getStone()) {
                sb.append(Utils.toRed(need.getStone()));
            } else {
                sb.append(need.getStone());
            }
            sb.append(Icon.STONE);
            if (needTree || needOre || needOil) sb.append("|");
        }

        if (needTree) {
            if (need.getTree() > have.getTree()) {
                sb.append(Utils.toRed(need.getTree()));
            } else {
                sb.append(need.getTree());
            }
            sb.append(Icon.TREE);
            if (needOre || needOil) sb.append("|");
        }

        if (needOre) {
            if (need.getOre() > have.getOre()) {
                sb.append(Utils.toRed(need.getOre()));
            } else {
                sb.append(need.getOre());
            }
            sb.append(Icon.ORE);
            if (needOil) sb.append("|");
        }

        if (needOil) {
            if (need.getOil() > have.getOil()) {
                sb.append(Utils.toRed(need.getOil()));
            } else {
                sb.append(need.getOil());
            }
            sb.append(Icon.OIL);
        }

        return sb.append("]").toString();
    }

    public static String forTradingRequest(TradingRequest tradingRequest) {
        StringBuilder sb = new StringBuilder();

        sb.append("📜#").append(tradingRequest.getId());

        sb.append(" : Игрок ").append(tradingRequest.getFromPlayer());

        switch (tradingRequest.getTradingType()) {
            case BUY -> {
                sb.append(" ⏪ Покупает ");
                sb.append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" по ");
                sb.append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency()).append("/шт");
            }
            case SELL -> {
                sb.append(" ⏩ Продает ");
                sb.append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" по ");
                sb.append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency()).append("/шт");
            }
            case SWAP -> {
                sb.append(" 🔄 Меняет ");
                sb.append(tradingRequest.getCountSelected()).append(tradingRequest.getSelectedCurrency()).append(" на ");
                sb.append(tradingRequest.getCountExpected()).append(tradingRequest.getExpectedCurrency());
            }
        }

        return sb.toString();
    }

}
