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

    private final StoreRepository storeRepository;

    private final CustomerRepository customerRepository;

    private final VoucherDiscountRepository voucherDiscountRepository;

    public FoodOrderService(StoreRepository storeRepository, CustomerRepository customerRepository,
                            VoucherDiscountRepository voucherDiscountRepository) {
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;
        this.voucherDiscountRepository = voucherDiscountRepository;
    }

    public FoodOrder createF(CreateFoodOrderRequest c) {

        FoodOrder pedido = new FoodOrder();
        pedido.setId(UUID.randomUUID().toString());
        pedido.setCreatedAt(LocalDateTime.now());

        if(c.getStoreId() == null && c.getStoreId().isEmpty()) {
            System.out.println("Store id null or empty.");
            throw new IllegalArgumentException();
        }

        try {
            storeRepository.findById(c.getStoreId());
        } catch (Exception e) {
            System.out.println("Store id not found.");
            throw new IllegalArgumentException();
        }

        if(c.getCustomerId() == null && c.getCustomerId().isEmpty()) {
            System.out.println("Customer id null or empty.");
            throw new IllegalArgumentException();
        }

        try {
            customerRepository.findById(c.getCustomerId());
        } catch (Exception e) {
            System.out.println("Customer id not found.");
            throw new IllegalArgumentException();
        }

        if(c.getOrderItemList().isEmpty()) {
            System.out.println("Order item list is empty.");
            throw new IllegalArgumentException();
        }

        // processa caso o cliente ainda nao tenha feito pagamento
        if(c.isPaid() == false) {
            VoucherDiscount vd = voucherDiscountRepository.findByIdAndCustomerId(c.getVoucherDiscountId(), c.getCustomerId());

            Double precoTotal = 0.0;

            for (FoodOrderItemRequest item: c.getOrderItemList()) {
                FoodItem foodItem = storeRepository.findItem(c.getStoreId(), item.getId());
                precoTotal = precoTotal + (foodItem.getPrice() * item.getAmount());
            }

            Double descontoPorMeioDePagamento = 0.0;

            if(c.getPaymentType().toString().equals("CASH")) {
                descontoPorMeioDePagamento = 0.5;
            }

            if(c.getPaymentType().toString().equals("PIX")) {
                descontoPorMeioDePagamento = 0.5;
            }

            if(c.getPaymentType().toString().equals("DEBIT_CARD")) {
                descontoPorMeioDePagamento = 0.5;
            }

            if(c.getPaymentType().toString().equals("CREDIT_CARD")) {
                descontoPorMeioDePagamento = 0.0;
            }

            precoTotal = precoTotal - (vd.getValue() + descontoPorMeioDePagamento);

            pedido.setTotalPrice(precoTotal);
        }

        return pedido;

    }
}
