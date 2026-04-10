package practice_12.task_5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"List<Product> getListProductsFilteredByPrice(String category, double from, double to)\"")
public class GetListProductsFilteredByPriceTest extends Preconditions {

    /**
     * Positive :
     * [20, 35] -> [w, c, j]
     * [21, 35] -> [c, j]
     * [21, 34] -> [j]
     * Что закрытый инвентарь не мешает
     *
     * Negative :
     * null -> IllegalArgumentException
     * empty -> IllegalArgumentException
     *
     * Поиск в пустом инвентаре -> OutOfStockException
     * [4, 10] -> OutOfStockException
     */

    @Test
    void getListProductsFilteredByPrice_ReturnsCorrectlyProductsList_WhenWeHaveFilteredProducts() {
        targetClass.setInventoryOpen();
        targetClass.addProductToInventory(water);
        targetClass.addProductToInventory(cocaCola);
        targetClass.addProductToInventory(juice);
        targetClass.setInventoryClose();

        assertEquals(3, targetClass.getListProductsFilteredByPrice(water.category(), 20, 35).size());
        assertEquals(2, targetClass.getListProductsFilteredByPrice(water.category(), 21, 35).size());
        assertEquals(1, targetClass.getListProductsFilteredByPrice(water.category(), 21, 34).size());
    }

    @Test
    void getListProductsFilteredByPrice_ReturnException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.getListProductsFilteredByPrice(null, 20, 35));
    }

    @Test
    void getListProductsFilteredByPrice_ReturnException_WhenInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.getListProductsFilteredByPrice("", 20, 35));
    }

    @Test
    void getListProductsFilteredByPrice_ReturnException_WhenInventoryIsEmpty() {
        assertTrue(targetClass.getInventory().isEmpty());
        assertThrows(OutOfStockException.class, () -> targetClass.getListProductsFilteredByPrice(water.category(), 20, 35));
    }

    @Test
    void getListProductsFilteredByPrice_ReturnException_WhenProductsHasNotHaveInCategory() {
        targetClass.setInventoryOpen();
        targetClass.addProductToInventory(water);
        targetClass.getInventory().get(water.category()).clear();

        assertEquals(1, targetClass.getInventory().size());
        assertTrue(targetClass.getInventory().get(water.category()).isEmpty());
        assertThrows(OutOfStockException.class, () -> targetClass.getListProductsFilteredByPrice(water.category(), 20, 35));
    }
}
