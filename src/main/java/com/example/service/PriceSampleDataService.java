package com.example.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PriceSampleDataService {

    // Hardcoded price map for predefined scenarios
    private final Map<String, Map<Integer, BigDecimal>> priceMap = new HashMap<>();

    public PriceSampleDataService() {
        // Initialize hardcoded price map
        Map<Integer, BigDecimal> productAPrices = new HashMap<>();
        productAPrices.put(1, BigDecimal.valueOf(40)); // 1 unit of A -> 40 cents
        productAPrices.put(2, BigDecimal.valueOf(80)); // 2 units of A -> 80 cents
        productAPrices.put(3, BigDecimal.valueOf(70)); // 3 units of A -> 70 cents
        productAPrices.put(4, BigDecimal.valueOf(110)); // 4 units of A -> 110 cents
        productAPrices.put(7, BigDecimal.valueOf(180)); // 7 units of A -> 180 cents

        Map<Integer, BigDecimal> productBPrices = new HashMap<>();
        productBPrices.put(1, BigDecimal.valueOf(10)); // 1 unit of B -> 10 cents
        productBPrices.put(2, BigDecimal.valueOf(15)); // 2 units of B -> 15 cents
        productBPrices.put(3, BigDecimal.valueOf(25)); // 3 units of B -> 25 cents
        productBPrices.put(5, BigDecimal.valueOf(45)); // 5 units of B -> 45 cents

        priceMap.put("A", productAPrices);
        priceMap.put("B", productBPrices);
    }

    /**
     * Returns the total price for the given SKU and quantity.
     *
     * @param sku      The product SKU (e.g., "A" or "B").
     * @param quantity The quantity of the product.
     * @return The total price in cents.
     * @throws IllegalArgumentException If the SKU or quantity is not predefined.
     */
    public BigDecimal getPrice(String sku, int quantity) {
        Map<Integer, BigDecimal> productPrices = priceMap.get(sku);

        if (productPrices == null || !productPrices.containsKey(quantity)) {
            throw new IllegalArgumentException("Price not defined for SKU: " + sku + ", Quantity: " + quantity);
        }

        return productPrices.get(quantity);
    }
}
