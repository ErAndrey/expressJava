package world_wars;

public final class Currency {
    private int gold, food, stone, tree, ore, oil;

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

    public void depositCurrency(Currency currency) {
        this.gold += currency.gold;
        this.food += currency.food;
        this.stone += currency.stone;
        this.tree += currency.tree;
        this.ore += currency.ore;
        this.oil += currency.oil;
        this.checkMinus();
    }
    public void withdrawCurrency(Currency currency) {
        this.gold -= currency.gold;
        this.food -= currency.food;
        this.stone -= currency.stone;
        this.tree -= currency.tree;
        this.ore -= currency.ore;
        this.oil -= currency.oil;
        this.checkMinus();
    }

    private void checkMinus() {
        if (this.gold < 0) this.gold = 0;
        if (this.food < 0) this.food = 0;
        if (this.stone < 0) this.stone = 0;
        if (this.tree < 0) this.tree = 0;
        if (this.ore < 0) this.ore = 0;
        if (this.oil < 0) this.oil = 0;
    }

    private String toStringSkipEmptyCurrency() {
        StringBuilder sb = new StringBuilder("[ ");
        if (gold > 0) {
            sb.append(gold + " " + Icon.GOLD);
            if (food > 0 || stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(" | ");
        }
        if (food > 0) {
            sb.append(food + " " + Icon.FOOD);
            if (stone > 0 || tree > 0 || ore > 0 || oil > 0) sb.append(" | ");
        }
        if (stone > 0) {
            sb.append(stone + " " + Icon.STONE);
            if (tree > 0 || ore > 0 || oil > 0) sb.append(" | ");
        }
        if (tree > 0) {
            sb.append(tree + " " + Icon.TREE);
            if (ore > 0 || oil > 0) sb.append(" | ");
        }
        if (ore > 0) {
            sb.append(ore + " " + Icon.ORE);
            if (oil > 0) sb.append(" | ");
        }
        if (oil > 0) sb.append(oil + " " + Icon.OIL);
        return sb.append(" ]").toString();
    }

    @Override
    public String toString() {
        return toStringSkipEmptyCurrency();
    }
}
