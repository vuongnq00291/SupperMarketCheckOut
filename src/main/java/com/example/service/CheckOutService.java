package com.example.service;

import com.example.domain.aggregate.Cart;
import com.example.domain.entity.Item;
import com.example.domain.valueobject.CartTotalSummary;
import com.example.domain.valueobject.ItemGroupPrice;

import java.math.BigDecimal;

/**
 * Service class responsible for handling the checkout process.
 * Each instance of this service is associated with a single customer's cart.
 * Simplifies operations such as scanning products and calculating totals.
 */
public class CheckOutService {

    // The cart associated with this checkout process
    private final Cart cart;

    // Service for retrieving price data, including unit prices and discount rules
    private final PriceSampleDataService priceService;

    // Sample ItemService
    private final ItemService itemService;

    /**
     * Constructs a new CheckOutService instance.
     * Each instance initializes a new Cart for the customer.
     * In production, consider caching carts (e.g., using a cache with UUIDs for persistence between actions).
     *
     * @param priceService The service used to fetch product pricing information.
     * @param itemService The service used to fetch item information.
     */
    public CheckOutService(PriceSampleDataService priceService, ItemService itemService) {
        this.itemService = itemService;
        this.cart = new Cart(); // New cart for each customer, better use TTL cache with UUID
        this.priceService = priceService;
    }

    /**
     * Scans a product and adds it to the cart.
     * If the product is already in the cart, its quantity is incremented.
     * Retrieves the updated price for the product based on the new quantity (e.g., multi-buy discounts).
     *
     * @param sku The SKU (Stock Keeping Unit) of the product being scanned.
     */
    public void scan(String sku) {
        // Get the current quantity of the product in the cart
        int quantity = cart.getItemQuantity(sku);

        // If the product is not yet in the cart, add it
        // avoid query item again
        if (quantity == 0) {
            Item item = itemService.getItem(sku);
            cart.add(item);
        }

        // Increment the product quantity
        quantity++;

        // Fetch the updated price based on the new quantity
        BigDecimal price = priceService.getPrice(sku, quantity);

        // Update the cart with the new quantity and price
        //by doing this. We are ensure only cart control the state of the item.
        cart.updateGroupPrice(sku, new ItemGroupPrice(price,quantity));
    }

    /**
     * Calculates the total cost of all items in the cart.
     * Generates a summary including:
     * - Total price for each SKU.
     * - Overall total price for the cart.
     *
     * @return A CartTotalSummary object containing the calculated totals.
     */
    public CartTotalSummary calculateTotal() {
        return cart.calculateTotal();
    }
}
