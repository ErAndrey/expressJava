package world_wars.general;

import world_wars.trading.CurrencyType;

public final class Currency {
    private int gold, food, stone, tree, ore, oil;

    public static Currency repeatCurrency(Currency currency, int repeats) {
        return new Currency(
                currency.gold * repeats,
                currency.food * repeats,
                currency.stone * repeats,
                currency.tree * repeats,
                currency.ore * repeats,
                currency.oil * repeats
        );
    }

    public static Currency of(int gold, int food, int stone, int tree, int ore, int oil) {
        return new Currency(gold, food, stone, tree, ore, oil);
    }

    private Currency(int gold, int food, int stone, int tree, int ore, int oil) {
        this.gold = gold;
        this.food = food;
        this.stone = stone;
        this.tree = tree;
        this.ore = ore;
        this.oil = oil;
    }

    public int get(CurrencyType currency) {
        return switch (currency) {
            case GOLD -> this.gold;
            case FOOD -> this.food;
            case STONE -> this.stone;
            case TREE -> this.tree;
            case ORE -> this.ore;
            case OIL -> this.oil;
        };
    }
    public void deposit(CurrencyType currency, int value) {
        switch (currency) {
            case GOLD -> this.gold += value;
            case FOOD -> this.food += value;
            case STONE -> this.stone += value;
            case TREE -> this.tree += value;
            case ORE -> this.ore += value;
            case OIL -> this.oil += value;
        }
    }
    public void withdraw(CurrencyType currency, int value) {
        switch (currency) {
            case GOLD -> this.gold -= value;
            case FOOD -> this.food -= value;
            case STONE -> this.stone -= value;
            case TREE -> this.tree -= value;
            case ORE -> this.ore -= value;
            case OIL -> this.oil -= value;
        }
    }
    public int getGold() { return this.gold; }
    public int getFood() { return this.food; }
    public int getStone() { return this.stone; }
    public int getTree() { return this.tree; }
    public int getOre() { return this.ore; }
    public int getOil() { return this.oil; }

    public boolean isEmptyCurrency() {
        return this.gold == 0 &&
                this.food == 0 &&
                this.stone == 0 &&
                this.tree == 0 &&
                this.ore == 0 &&
                this.oil == 0;
    }

    public static Currency getMiddleCurrency(Currency currency) {
        Currency toReturn = Currency.of(0,0,0,0,0,0);
        if (currency.gold > 0) toReturn.gold = currency.gold / 2;
        if (currency.food > 0) toReturn.food = currency.food / 2;
        if (currency.stone > 0) toReturn.stone = currency.stone / 2;
        if (currency.tree > 0) toReturn.tree = currency.tree / 2;
        if (currency.ore > 0) toReturn.ore = currency.ore / 2;
        if (currency.oil > 0) toReturn.oil = currency.oil / 2;
        return toReturn;
    }

    public void depositCurrency(Currency currency) {
        this.gold += currency.gold;
        this.food += currency.food;
        this.stone += currency.stone;
        this.tree += currency.tree;
        this.ore += currency.ore;
        this.oil += currency.oil;
        //this.checkMinus();
    }
    public void withdrawCurrency(Currency currency) {
        this.gold -= currency.gold;
        this.food -= currency.food;
        this.stone -= currency.stone;
        this.tree -= currency.tree;
        this.ore -= currency.ore;
        this.oil -= currency.oil;
        //this.checkMinus();
    }

    //toDo ресетит changeBalance
    private void checkMinus() {
        if (this.gold < 0) this.gold = 0;
        if (this.food < 0) this.food = 0;
        if (this.stone < 0) this.stone = 0;
        if (this.tree < 0) this.tree = 0;
        if (this.ore < 0) this.ore = 0;
        if (this.oil < 0) this.oil = 0;
    }

    private String toStringSkipEmptyCurrency() {
        if (isEmptyCurrency()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        String del = "|";
        if (gold > 0) {
            sb.append(gold).append(Icon.GOLD);
            if (food > 0 || stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (food > 0) {
            sb.append(food).append(Icon.FOOD);
            if (stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (stone > 0) {
            sb.append(stone).append(Icon.STONE);
            if (tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (tree > 0) {
            sb.append(tree).append(Icon.TREE);
            if (ore > 0 || oil > 0) sb.append(del);
        }
        if (ore > 0) {
            sb.append(ore).append(Icon.ORE);
            if (oil > 0) sb.append(del);
        }
        if (oil > 0) sb.append(oil).append(Icon.OIL);
        return sb.append("]").toString();
    }

    private String toStringSkipEmptyCurrency(boolean isProduce) {
        if (isEmptyCurrency()) return "";
        StringBuilder sb = new StringBuilder("[");
        String del = "|";
        if (gold > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + gold));
            } else {
                sb.append(Utils.toRed("" + gold));
            }
            sb.append(Icon.GOLD);
            if (food > 0 || stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (food > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + food));
            } else {
                sb.append(Utils.toRed("" + food));
            }
            sb.append(Icon.FOOD);
            if (stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (stone > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + stone));
            } else {
                sb.append(Utils.toRed("" + stone));
            }
            sb.append(Icon.STONE);
            if (tree > 0 || ore > 0 || oil > 0) sb.append(del);
        }
        if (tree > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + tree));
            } else {
                sb.append(Utils.toRed("" + tree));
            }
            sb.append(Icon.TREE);
            if (ore > 0 || oil > 0) sb.append(del);
        }
        if (ore > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + ore));
            } else {
                sb.append(Utils.toRed("" + ore));
            }
            sb.append(Icon.ORE);
            if (oil > 0) sb.append(del);
        }
        if (oil > 0) {
            if (isProduce) {
                sb.append(Utils.toGreen("" + oil));
            } else {
                sb.append(Utils.toRed("" + oil));
            }
            sb.append(Icon.OIL);
        }
        return sb.append("]").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Currency other = (Currency) o;
        return this.gold == other.gold &&
                this.food == other.food &&
                this.stone == other.stone &&
                this.tree == other.tree &&
                this.ore == other.ore &&
                this.oil == other.oil;
    }

    @Override
    public String toString() {
        return toStringSkipEmptyCurrency();
    }

    public String toString(boolean isProduce) {
        return ToString.forCurrency(this, isProduce);
    }
}
