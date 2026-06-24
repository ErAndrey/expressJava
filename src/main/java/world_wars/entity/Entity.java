package world_wars.entity;

import java.util.Objects;

public abstract class Entity {
    private static int counter;

    private final int id;
    protected int lvl;
    protected int defence;

    public Entity() { this.id = counter++; this.lvl = 1; }
    public Entity(int lvl) { this.id = counter++; this.lvl = lvl; }

    public int getId() { return this.id; }
    public int getLvl() { return this.lvl; }
    public int getDefence() { return this.defence; }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Entity entity = (Entity) o;
        return this.id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
