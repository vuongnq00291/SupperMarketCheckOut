package com.example.domain.valueobject;


import java.math.BigDecimal;

public record ItemGroupPrice(BigDecimal value,int quantity) {
    public ItemGroupPrice {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money value must be non-negative.");
        }
    }

}
