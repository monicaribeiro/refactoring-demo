package com.demo.refactoring.repository;

import com.demo.refactoring.domain.FoodItem;
import org.springframework.stereotype.Component;

@Component
public class StoreRepository {

    public String findById(String id) {
        return "Mock - loja found.";
    }

    public FoodItem findItem(String storeId, String itemId) {
        return new FoodItem(itemId, 10.00);
    }
}
