package com.example.service;

import com.example.domain.valueobject.CartTotalSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
public class CheckoutServiceTest {

    private CheckOutService checkoutService;
    private PriceSampleDataService priceService;
    private ItemService itemService;
    @BeforeEach
    void setUp() {
        priceService = new PriceSampleDataService();
        itemService = new ItemService();
    }

    @Test
    void testScanProductAndCalculateTotal() {
        checkoutService = new CheckOutService(priceService,itemService);
        // Scan products
        checkoutService.scan("A");
        checkoutService.scan("A");
        checkoutService.scan("B");
        checkoutService.scan("A");
        checkoutService.scan("B");
        // Calculate total
        CartTotalSummary totalSummary = checkoutService.calculateTotal();

        // Verify the results
        assertEquals(BigDecimal.valueOf(70), totalSummary.priceEachGroup().get("A").value());
        assertEquals(3, totalSummary.priceEachGroup().get("A").quantity());
        assertEquals(BigDecimal.valueOf(15), totalSummary.priceEachGroup().get("B").value());
        assertEquals(2, totalSummary.priceEachGroup().get("B").quantity());
        assertEquals(BigDecimal.valueOf(85), totalSummary.totalPrice().value());
    }

}
