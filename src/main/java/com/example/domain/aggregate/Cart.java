package com.example.domain.aggregate;

import com.example.domain.entity.Item;
import com.example.domain.valueobject.CartTotalSummary;
import com.example.domain.valueobject.ItemGroupPrice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the shopping cart aggregate in the domain layer.
 * Manages items added to the cart and calculates total prices.
 */
public class Cart {

    // A map to store CartItems, keyed by their SKU (Stock Keeping Unit).
    private final Map<String, Item> items = new HashMap<>();

    /**
    * Add item only if it is not in cart.
     * @param item
     */
    public void add(Item item) {
        if (item == null || item.sku() == null || item.sku().isEmpty()) {
            throw new IllegalArgumentException("item or sku cannot be null or empty");
        }
        items.putIfAbsent(item.sku(), item);
    }

    /**
     * Updates the quantity and price of a specific item in the cart.
     *
     *
     * @param sku      The SKU of the item to update.
     * @param groupPrice The updated price based on quantity of the item.
     */
    public void updateGroupPrice(String sku, ItemGroupPrice groupPrice) {
        Item item = items.get(sku);
        if (item == null) {
            throw new IllegalArgumentException("Item with SKU " + sku + " does not exist in the cart.");
        }
        Item updatedItem = item.withUpdatedGroupPrice(groupPrice);
        items.put(sku, updatedItem);
    }

    /**
     * Calculates the total price of all items in the cart.
     *
     * @return A CartTotalSummary object containing:
     *         - The total price for each SKU.
     *         - The overall total price for the entire cart.
     */
    public CartTotalSummary calculateTotal() {
        // A map to store total prices by SKU.
        Map<String, ItemGroupPrice> priceEachGroup = new HashMap<>();
        // Initialize the overall total price to zero.
        BigDecimal totalPrice = BigDecimal.ZERO;

        // Iterate through each CartItem to calculate totals.
        for (Item item : items.values()) {
            ItemGroupPrice itemTotal = item.groupPrice();
            priceEachGroup.put(item.sku(), itemTotal);
            totalPrice = totalPrice.add(itemTotal.value());
        }

        // Return a summary of totals for the cart.
        ItemGroupPrice groupPrice = new ItemGroupPrice(totalPrice,0);
        return new CartTotalSummary(priceEachGroup, groupPrice);
    }

    /**
     * Retrieves the quantity of a specific item in the cart.
     * If the item does not exist, returns a default quantity of 0.
     *
     * @param sku The SKU of the item.
     * @return The quantity of the item in the cart.
     */
    public int getItemQuantity(String sku) {
        Item cartItem = items.get(sku);
        return cartItem!=null?cartItem.groupPrice().quantity():0;
    }
}
