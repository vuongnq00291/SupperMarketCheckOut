package com.example;

import com.example.domain.valueobject.CartTotalSummary;
import com.example.service.CheckOutService;
import com.example.service.ItemService;
import com.example.service.PriceSampleDataService;

public class Main {
    public static void main(String[] args) {
        // Initialize dependencies
        PriceSampleDataService priceService = new PriceSampleDataService();
        ItemService itemService = new ItemService();
        CheckOutService checkoutService = new CheckOutService(priceService,itemService);
        // Simulate scanning products
        System.out.println("Scanning products...");
        checkoutService.scan("A");
        checkoutService.scan("A");
        checkoutService.scan("B");
        checkoutService.scan("A");
        checkoutService.scan("B");
        checkoutService.scan("B");
        // Calculate total
        CartTotalSummary totalSummary = checkoutService.calculateTotal();
        // Print total prices for each SKU
        System.out.println("\nTotal Prices Per SKU:");
        totalSummary.priceEachGroup().forEach((sku, price) -> {
            System.out.println("SKU: " + sku + ", quantity "+ price.quantity() +" Total Price: " + price.value());
        });
        // Print overall total price
        System.out.println("\nOverall Total Price: " + totalSummary.totalPrice().value());
    }
}
