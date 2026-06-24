package world_wars.entity;

import world_wars.Currency;
import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.interfaces.Consuming;
import world_wars.interfaces.Producing;

public abstract class Build extends Entity implements Consuming, Producing {
    protected BuildType type;
    protected boolean isWork; // Если денег на обеспечение не хватает, то здание перестает функционировать

    public Build() { super(); }
    public Build(int lvl) { super(lvl); }

    public BuildType getType() { return this.type; }

    public boolean isWork() { return this.isWork; }
    public void stopWork() { this.isWork = false; }
    public void startWork() { this.isWork = true; }

    //toDo ?
    // Хранить consume и produce в полях? тогда в штате можно for each иметь актуальный баланс? или оставить
    // И тогда upgrade() можно ресетить внутри зданий
    // Или
    // В штате updateChangeBalanceAfterUpgrade(Build build)
    // - который прибавляет к changeBalance разницу между прошлым уровнем здания и текущим
    @Override
    public Currency consume() { return Consume.getConsume(this); }

    @Override
    public Currency produce() { return Produce.getProduce(this); }

    @Override
    public String toString() { return this.type.toString(); }
}
