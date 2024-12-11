package com.example.domain.valueobject;

import java.util.Map;

public record CartTotalSummary(Map<String, ItemGroupPrice> priceEachGroup, ItemGroupPrice totalPrice) {
}
