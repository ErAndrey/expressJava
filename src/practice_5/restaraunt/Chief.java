package practice_5.restaraunt;

public class Chief {
    public void addDishToMenu(Menu menu, Dish dish) {
        menu.addDish(dish);
    }

    public void checkDish(Dish dish) {
        dish.getInfo();
    }
}
