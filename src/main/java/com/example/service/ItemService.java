package com.example.service;

import com.example.domain.entity.Item;

public class ItemService {
    public Item getItem(String sku){
        return new Item(sku); //get it from db for external source.
    }
}
