package practice_5.restaraunt;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Dish> dishes;

    public Menu() {
        this.dishes = new ArrayList<>();
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public void readMenu() {
        System.out.println("Меню:");
        for (Dish dish : dishes) {
            dish.getInfo();
        }
    }

}
