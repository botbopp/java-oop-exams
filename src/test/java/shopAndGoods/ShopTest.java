package shopAndGoods;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopTest {

    private static final int DEFAULT_NUMBER_OF_SHELVES = 12;
    private static final String FIRST_GOODS_NAME = "Pens";
    private static final String FIRST_GOODS_CODE = "ABC";
    private static final String SECOND_GOODS_NAME = "Notebooks";
    private static final String SECOND_GOODS_CODE = "XYZ";
    private static final String FIRST_SHELF = "Shelves1";
    private static final String SECOND_SHELF = "Shelves2";
    private static final String FAKE = "FakeShelf";

    private Shop shop;
    private Goods firstGoods;
    private Goods secondGoods;

    @Before
    public void setUp() {
        shop = new Shop();
        firstGoods = new Goods(FIRST_GOODS_NAME, FIRST_GOODS_CODE);
        secondGoods = new Goods(SECOND_GOODS_NAME, SECOND_GOODS_CODE);
    }

    @Test
    public void test_CreatesShop_WithDefault_NumberOfShelves() {
        assertEquals(DEFAULT_NUMBER_OF_SHELVES, shop.getShelves().size());
    }

    @Test
    public void test_CreatesShop_With_EmptyShelves() {
        Map<String, Goods> shelves = shop.getShelves();
        for (Goods goods : shelves.values()) {
            assertNull(goods);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AddGoods_DoesNotAdd_WhenShelf_DoesNotExist() throws OperationNotSupportedException {
        shop.addGoods(FAKE, firstGoods);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void test_AddGoods_DoesNotAdd_WhenGoods_AlreadyAdded() throws OperationNotSupportedException {
        shop.addGoods(FIRST_SHELF, firstGoods);
        shop.addGoods(SECOND_SHELF, firstGoods);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AddGoods_DoesNotAdd_WhenShelf_AlreadyTaken() throws OperationNotSupportedException {
        shop.addGoods(FIRST_SHELF, firstGoods);
        shop.addGoods(FIRST_SHELF, secondGoods);
    }

    @Test
    public void test_AddGoods_AddsWhen_OperationSupported() throws OperationNotSupportedException {
        shop.addGoods(FIRST_SHELF, firstGoods);
        assertTrue(shop.getShelves().containsValue(firstGoods));
        assertEquals(firstGoods, shop.getShelves().get(FIRST_SHELF));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_RemoveGoods_DoesNotRemove_WhenShelf_DoesNotExist() {
        shop.removeGoods(FAKE, firstGoods);
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_RemoveGoods_DoesNotRemove_WhenGoods_NotOnThatShelf() throws OperationNotSupportedException {
        shop.addGoods(FIRST_SHELF, firstGoods);
        shop.removeGoods(FIRST_SHELF, secondGoods);
    }

    @Test
    public void test_RemoveGoods_RemovesWhen_OperationSupported() throws OperationNotSupportedException {
        shop.addGoods(FIRST_SHELF, firstGoods);
        shop.removeGoods(FIRST_SHELF, firstGoods);
        assertNull(shop.getShelves().get(FIRST_SHELF));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_GetShelves_Returns_UnmodifiableMap() {
        Map<String, Goods> shelves = shop.getShelves();
        shelves.remove(firstGoods);
    }

}