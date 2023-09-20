package com.demo.refactoring.controller.request;

import javax.validation.constraints.NotNull;

public class FoodOrderContactRequest {

    @NotNull
    private String value;

    @NotNull
    private FoodOrderContactTypeRequestEnum type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FoodOrderContactTypeRequestEnum getType() {
        return type;
    }

    public void setType(FoodOrderContactTypeRequestEnum type) {
        this.type = type;
    }
}
