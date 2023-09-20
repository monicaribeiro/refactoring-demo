package com.demo.refactoring.domain;

public class VoucherDiscount {

    private Double value;

    public VoucherDiscount(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
