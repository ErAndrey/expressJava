package practice_5.restaraunt;

public abstract class Dish {
    private String description;
    private double cost;

    public Dish(String description, double cost) {
        this.description = description;
        this.cost = cost;
    }

    protected String getDescription() {
        return this.description;
    }

    protected double getCost() {
        return this.cost;
    }

    public abstract void getInfo();

}
