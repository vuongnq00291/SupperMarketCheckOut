package com.example.domain.aggregate;

import com.example.domain.entity.Item;
import com.example.domain.valueobject.CartTotalSummary;
import com.example.domain.valueobject.ItemGroupPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void testAddItem() {
        cart.add(new Item("A"));
        cart.add(new Item("B"));

        assertEquals(1, cart.getItemQuantity("A"));
        assertEquals(1, cart.getItemQuantity("B"));
    }

    @Test
    void testUpdateItemQuantityPrice() {
        cart.add(new Item("A"));
        cart.updateGroupPrice("A", new ItemGroupPrice(BigDecimal.valueOf(40),2));

        assertEquals(2, cart.getItemQuantity("A"));
        assertEquals(BigDecimal.valueOf(40), cart.calculateTotal().priceEachGroup().get("A").value());
    }

    @Test
    void testCalculateTotal() {
        cart.add(new Item("A"));
        cart.updateGroupPrice("A", new ItemGroupPrice(BigDecimal.valueOf(40),2));

        cart.add(new Item("B"));
        cart.updateGroupPrice("B", new ItemGroupPrice(BigDecimal.valueOf(10),2));

        CartTotalSummary totalSummary = cart.calculateTotal();

        Map<String, ItemGroupPrice> totalBySku = totalSummary.priceEachGroup();
        assertEquals(BigDecimal.valueOf(40), totalBySku.get("A").value());
        assertEquals(BigDecimal.valueOf(10), totalBySku.get("B").value());
        assertEquals(BigDecimal.valueOf(50), totalSummary.totalPrice().value());
    }

    @Test
    void testAddThrowsExceptionForInvalidSku() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cart.add(null));
        assertEquals("item or sku cannot be null or empty", exception.getMessage());
    }


    @Test
    void testUpdateGroupPriceInvalidSku() {
        // Arrange
        String invalidSku = "INVALID_SKU";
        ItemGroupPrice groupPrice = new ItemGroupPrice(BigDecimal.valueOf(15.00), 2);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cart.updateGroupPrice(invalidSku, groupPrice);
        });

        assertEquals("Item with SKU INVALID_SKU does not exist in the cart.", exception.getMessage());
    }
}
