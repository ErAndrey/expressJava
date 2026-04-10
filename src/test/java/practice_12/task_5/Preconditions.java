package practice_12.task_5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Preconditions for Tests \"InventoryService\"")
public class Preconditions {
    protected InventoryService targetClass;
    protected Product water;
    protected Product cocaCola;
    protected Product juice;
    protected Product meat;
    protected Product chicken;


    @BeforeEach
    void getPreconditions() {
        this.targetClass = new InventoryService();
        this.water = new Product("Вода", "Напитки", 20.0);
        this.cocaCola = new Product("Кока кола", "Напитки", 35.0);
        this.juice = new Product("Сок", "Напитки", 30.0);
        this.meat = new Product("Мясо", "Мясное", 120.0);
        this.chicken = new Product("Курица", "Мясное", 100.0);
    }
}
