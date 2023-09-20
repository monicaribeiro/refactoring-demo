package com.demo.refactoring.repository;

import com.demo.refactoring.domain.VoucherDiscount;
import org.springframework.stereotype.Component;

@Component
public class VoucherDiscountRepository {

    public VoucherDiscount findByIdAndCustomerId(String id, String customerId) {
        return new VoucherDiscount(12.34);
    }
}
