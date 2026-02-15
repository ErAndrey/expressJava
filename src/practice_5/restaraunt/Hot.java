package practice_5.restaraunt;

public class Hot extends Dish {
    private double temperature;

    public Hot(String description, double cost, double temperature) {
        super(description, cost);
        this.temperature = temperature;
    }

    public double getTemperature() {
        return this.temperature;
    }

    @Override
    public void getInfo() {
        System.out.println("Описание: " + this.getDescription() + ", Стоимость: " + this.getCost() + "Руб, Температура: " + this.temperature + "С");
    }

}
