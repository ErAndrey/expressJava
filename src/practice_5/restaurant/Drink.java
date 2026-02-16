package practice_5.restaurant;

public class Drink extends Dish {
    private double volume;

    public Drink(String description, double cost, double volume) {
        super(description, cost);
        this.volume = volume;
    }

    public double getVolume() {
        return this.volume;
    }

    @Override
    public void getInfo() {
        System.out.println("Описание: " + this.getDescription() + ", Стоимость: " + this.getCost() + "Руб, Объем: " + this.volume + " литр");
    }

}
