package practice_12.task_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    private final Map<String, List<Product>> inventory;
    private boolean isInventoryOpen;

    public InventoryService() {
        this.inventory = new HashMap<>();
        this.isInventoryOpen = false;
    }

    public void setInventoryOpen() {
        this.isInventoryOpen = true;
    }

    public void setInventoryClose() {
        this.isInventoryOpen = false;
    }

    public boolean isInventoryOpen() {
        return this.isInventoryOpen;
    }

    public Map<String, List<Product>> getInventory() {
        return this.inventory;
    }

    public void addProductToInventory(Product product) {
        if (!isInventoryOpen) throw new UnsupportedOperationException("Инвентарь закрыт");
        if (product == null) throw new IllegalArgumentException("Продукт не может быть null");
        if (product.category() == null || product.category().isEmpty() || product.name() == null) throw new IllegalArgumentException("Категория и имя продукта не может быть null или empty");

        this.inventory.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
    }

    public List<Product> getListProductsByCategory(String category) {
        if (category == null || category.isEmpty()) throw new IllegalArgumentException("Искомая категория не может быть null или empty");
        if (this.inventory.isEmpty()) throw new OutOfStockException("Инвентарь пуст");

        List<Product> products = this.inventory.get(category);
        if (products == null || products.isEmpty()) throw new OutOfStockException("В указанной категории нет товаров");

        return products;
    }

    public List<Product> getListProductsFilteredByPrice(String category, double from, double to) {
        return getListProductsByCategory(category)
                .stream()
                .filter(product -> product.price() >= from && product.price() <= to)
                .toList();
    }
}
