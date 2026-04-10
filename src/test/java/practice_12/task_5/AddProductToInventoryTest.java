package practice_12.task_5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"void addProductToInventory(Product product)\"")
public class AddProductToInventoryTest extends Preconditions {

    /**
     * Positive : (inventory open)
     * Проверка открытия и закрытия инвенторя
     *
     * Добавить 1 продукт -> Добавился один продукт
     * Добавить 2 продукта из одной категории -> Добавились 2 продукта, Запись инвенторя 1
     * Добавить разные продукы в разные категории
     *
     * Negative :
     * Инвентарь закрыт -> UnsupportedOperationException
     * null -> IllegalArgumentException
     * поля продукта null -> IllegalArgumentException
     */

    @Test
    void openAndCloseInventoryTest() {
        assertFalse(targetClass.isInventoryOpen());

        targetClass.setInventoryOpen();
        assertTrue(targetClass.isInventoryOpen());

        targetClass.setInventoryClose();
        assertFalse(targetClass.isInventoryOpen());
    }

    @Test
    void addProductToInventory_AddOneProduct() {
        targetClass.setInventoryOpen();

        assertTrue(targetClass.getInventory().isEmpty());
        targetClass.addProductToInventory(water);

        assertEquals(1, targetClass.getInventory().size());
        assertEquals(1, targetClass.getInventory().get(water.category()).size());

        Product actual = targetClass.getInventory().get(water.category()).getFirst();
        assertEquals(water, actual);

        assertEquals(water.name(), actual.name());
        assertEquals(water.category(), actual.category());
        assertEquals(water.price(), actual.price());
    }

    @Test
    void addProductToInventory_AddTwoProductInEqualCategory() {
        targetClass.setInventoryOpen();

        targetClass.addProductToInventory(water);
        targetClass.addProductToInventory(cocaCola);

        assertEquals(1, targetClass.getInventory().size());
        assertEquals(2, targetClass.getInventory().get(water.category()).size());
    }

    @Test
    void addProductToInventory_AddSameProductsToSameCategory() {
        targetClass.setInventoryOpen();

        targetClass.addProductToInventory(water);
        targetClass.addProductToInventory(juice);

        targetClass.addProductToInventory(meat);
        targetClass.addProductToInventory(chicken);

        targetClass.addProductToInventory(new Product("Ложка", "Инструмент", 50.0));

        assertEquals(3, targetClass.getInventory().size());

        assertEquals(2, targetClass.getInventory().get(water.category()).size());
        assertEquals(2, targetClass.getInventory().get(meat.category()).size());
        assertEquals(1, targetClass.getInventory().get("Инструмент").size());
    }

    @Test
    void addProductToInventory_ReturnsException_WhenInventoryIsClosed() {
        assertThrows(UnsupportedOperationException.class, () -> targetClass.addProductToInventory(chicken));
    }

    @Test
    void addProductToInventory_ReturnsException_WhenInputIsNull() {
        targetClass.setInventoryOpen();
        assertThrows(IllegalArgumentException.class, () -> targetClass.addProductToInventory(null));
    }

    @Test
    void addProductToInventory_ReturnsException_WhenProductNameIsNull() {
        targetClass.setInventoryOpen();
        assertThrows(IllegalArgumentException.class, () -> targetClass.addProductToInventory(new Product(null, "Вода", 1)));
    }

    void addProductToInventory_ReturnsException_WhenProductCategoryIsNullOrEmpty() {
        targetClass.setInventoryOpen();
        assertThrows(IllegalArgumentException.class, () -> targetClass.addProductToInventory(new Product("Вода", null, 1)));
        assertThrows(IllegalArgumentException.class, () -> targetClass.addProductToInventory(new Product("Вода", "", 2)));
    }
}
