package com.demo.refactoring.service;

import com.demo.refactoring.controller.request.CreateFoodOrderRequest;
import com.demo.refactoring.controller.request.FoodOrderItemRequest;
import com.demo.refactoring.domain.FoodItem;
import com.demo.refactoring.domain.FoodOrder;
import com.demo.refactoring.domain.VoucherDiscount;
import com.demo.refactoring.repository.CustomerRepository;
import com.demo.refactoring.repository.StoreRepository;
import com.demo.refactoring.repository.VoucherDiscountRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FoodOrderService {

    public static final double DISCOUNT_CASH = 0.5;
    public static final double DISCOUNT_DEFAULT = 0.0;

    private final StoreRepository storeRepository;

    private final CustomerRepository customerRepository;

    private final VoucherDiscountRepository voucherDiscountRepository;

    public FoodOrderService(StoreRepository storeRepository, CustomerRepository customerRepository,
                            VoucherDiscountRepository voucherDiscountRepository) {
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.voucherDiscountRepository = voucherDiscountRepository;
    }

    public FoodOrder createFoodOrder(CreateFoodOrderRequest orderRequest) {

        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setId(UUID.randomUUID().toString());
        foodOrder.setCreatedAt(LocalDateTime.now());

        checkIfStoreIdExists(orderRequest);

        checkIfCustomerIdExists(orderRequest);

        if(!orderRequest.isPaid()) {
            calculateTotalPrice(orderRequest, foodOrder);
        }

        return foodOrder;
    }

    private void calculateTotalPrice(CreateFoodOrderRequest orderRequest, FoodOrder foodOrder) {
        VoucherDiscount voucherDiscount = voucherDiscountRepository.findByIdAndCustomerId(orderRequest.getVoucherDiscountId(), orderRequest.getCustomerId());
        double totalPrice = 0.0;
        totalPrice = getTotalPriceByOrderItems(orderRequest, totalPrice);
        Double discountPaymentMethod = getDiscountPaymentMethod(orderRequest);
        foodOrder.setTotalPrice(getTotalPrice(totalPrice, voucherDiscount, discountPaymentMethod));
    }

    private static double getTotalPrice(double totalPrice, VoucherDiscount voucherDiscount, Double discountPaymentMethod) {
        return totalPrice - (voucherDiscount.getValue() + discountPaymentMethod);
    }

    private Double getDiscountPaymentMethod(CreateFoodOrderRequest orderRequest) {
        return switch (orderRequest.getPaymentType()) {
            case PIX, DEBIT_CARD, CASH -> DISCOUNT_CASH;
            default -> DISCOUNT_DEFAULT;
        };
    }

    private double getTotalPriceByOrderItems(CreateFoodOrderRequest orderRequest, double totalPrice) {
        for (FoodOrderItemRequest item: orderRequest.getOrderItemList()) {
            FoodItem foodItem = storeRepository.findItem(orderRequest.getStoreId(), item.getId());
            totalPrice = totalPrice + (foodItem.getPrice() * item.getAmount());
        }
        return totalPrice;
    }

    private void checkIfCustomerIdExists(CreateFoodOrderRequest orderRequest) {
        try {
            customerRepository.findById(orderRequest.getCustomerId());
        } catch (Exception e) {
            System.out.println("Customer id not found.");
            throw new IllegalArgumentException();
        }
    }

    private void checkIfStoreIdExists(CreateFoodOrderRequest orderRequest) {
        try {
            storeRepository.findById(orderRequest.getStoreId());
        } catch (Exception e) {
            System.out.println("Store id not found.");
            throw new IllegalArgumentException();
        }
    }
}
