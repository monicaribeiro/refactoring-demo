package com.demo.refactoring.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public class CreateFoodOrderRequest {

    @NotBlank
    private String storeId;

    @NotBlank
    private String customerId;

    @NotBlank
    private List<FoodOrderItemRequest> orderItemList;

    @NotNull
    private FoodOrderContactRequest contact;

    @NotNull
    private boolean paid;

    @NotNull
    private PaymentTypeEnumRequest paymentType;

    @NotNull
    private String voucherDiscountId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<FoodOrderItemRequest> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<FoodOrderItemRequest> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public FoodOrderContactRequest getContact() {
        return contact;
    }

    public void setContact(FoodOrderContactRequest contact) {
        this.contact = contact;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public PaymentTypeEnumRequest getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnumRequest paymentType) {
        this.paymentType = paymentType;
    }

    public String getVoucherDiscountId() {
        return voucherDiscountId;
    }

    public void setVoucherDiscountId(String voucherDiscountId) {
        this.voucherDiscountId = voucherDiscountId;
    }
}
