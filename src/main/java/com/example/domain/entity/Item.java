package com.example.domain.entity;

import com.example.domain.valueobject.ItemGroupPrice;

import java.math.BigDecimal;

public record Item(String sku, ItemGroupPrice groupPrice) {
   //item could have more fields like , name, ean , des ...
    public Item(String sku) {
        this(sku, new ItemGroupPrice(BigDecimal.ZERO, 1));
    }

    /**
     * Creates a new instance of Item with an updated group price.
     *
     * @param newGroupPrice The new group price to set.
     * @return A new Item instance with the updated group price.
     */
    public Item withUpdatedGroupPrice(ItemGroupPrice newGroupPrice) {
        return new Item(this.sku, newGroupPrice);
    }
}
