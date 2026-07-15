package world_wars.entity;

import world_wars.general.Currency;

public abstract class Entity {
    private static int counter;

    private final int id;
    protected int lvl;
    protected int defence;
    protected Currency consume;

    public Entity() { this.id = ++counter; this.lvl = 1; }
    public Entity(int lvl) { this.id = ++counter; this.lvl = lvl; }

    public int getId() { return this.id; }
    public int getLvl() { return this.lvl; }
    public int getDefence() { return this.defence; }
    public Currency getConsume() { return this.consume; }
}
