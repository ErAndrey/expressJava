package practice_5.restaurant;

public class Chef {
    public void addDishToMenu(Menu menu, Dish dish) {
        menu.addDish(dish);
    }

    public void checkDish(Dish dish) {
        dish.getInfo();
    }
}
