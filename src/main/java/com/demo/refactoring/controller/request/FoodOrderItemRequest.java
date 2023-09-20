package com.demo.refactoring.controller.request;

import javax.validation.constraints.NotNull;

public class FoodOrderItemRequest {

    @NotNull
    private String id;

    @NotNull
    private Integer amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
