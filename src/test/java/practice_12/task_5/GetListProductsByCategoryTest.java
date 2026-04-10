package practice_12.task_5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for : \"List<Product> getListProductsByCategory(String category)\"")
public class GetListProductsByCategoryTest extends Preconditions {

    /**
     * Positive :
     * Есть категория 1 -> Получили лист продуктов (1)
     * Есть категория 1 -> Получили лист продуктов (3)
     * Проверяем, что не обязательно инвентарь должен быть закрыт
     *
     * Negative :
     * null -> IllegalArgumentException
     * empty -> IllegalArgumentException
     * Нет товаров в категории -> OutOfStockException
     */

    @Test
    void getListProductsByCategory_ReturnsListWithOneElementInSameCategory() {
        targetClass.setInventoryOpen();
        targetClass.addProductToInventory(water);
        targetClass.addProductToInventory(chicken);
        targetClass.setInventoryClose();

        assertEquals(1, targetClass.getListProductsByCategory(water.category()).size());
        assertEquals(1, targetClass.getListProductsByCategory(chicken.category()).size());
    }

    @Test
    void getListProductsByCategory_ReturnsListWithThreeElementsInOneCategory() {
        targetClass.setInventoryOpen();
        targetClass.addProductToInventory(water);
        targetClass.addProductToInventory(cocaCola);
        targetClass.addProductToInventory(juice);
        targetClass.setInventoryClose();

        assertEquals(3, targetClass.getListProductsByCategory(water.category()).size());
    }

    @Test
    void getListProductsByCategory_ReturnsException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.getListProductsByCategory(null));
    }

    @Test
    void getListProductsByCategory_ReturnsException_WhenInputIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> targetClass.getListProductsByCategory(""));
    }

    @Test
    void getListProductsByCategory_ReturnsException_WhenCategoryHasNotHaveElements() {
        targetClass.setInventoryOpen();
        targetClass.addProductToInventory(water);
        targetClass.getInventory().get(water.category()).clear();

        assertEquals(1, targetClass.getInventory().size());
        assertTrue(targetClass.getInventory().get(water.category()).isEmpty());

        assertThrows(OutOfStockException.class, () -> targetClass.getListProductsByCategory(water.category()));
    }
}
