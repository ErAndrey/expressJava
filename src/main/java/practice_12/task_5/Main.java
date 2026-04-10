package practice_12.task_5;

public class Main {
    public static void main(String[] args) {
        Product chez = new Product("Сыр", "Молочка", 24.5);
        Product water = new Product("Святой источник", "Вода", 5);
        Product chicken = new Product("Бедро курицы", "Мясо", 50.5);
        Product beef = new Product("Стейк", "Мясо", 56.5);
        Product egg = new Product("Куриное яйцо 10шт", "Яйца", 55.5);

        InventoryService inventory = new InventoryService();

        System.out.println(inventory.isInventoryOpen());

        inventory.setInventoryOpen();
        inventory.addProductToInventory(water);
        inventory.addProductToInventory(chicken);
        inventory.addProductToInventory(beef);
        System.out.println(inventory.getListProductsByCategory("Мясо"));
        System.out.println(inventory.getListProductsFilteredByPrice("Мясо", 51, 60));
    }
}
