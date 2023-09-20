package com.demo.refactoring.controller;

import com.demo.refactoring.controller.request.CreateFoodOrderRequest;
import com.demo.refactoring.domain.FoodOrder;
import com.demo.refactoring.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/food-order")
public class FoodOrderController {

    @Autowired
    private final FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodOrder> createFoodOrder(@RequestBody @Valid CreateFoodOrderRequest createUserRequest) {
        return ResponseEntity.ok(foodOrderService.createFoodOrder(createUserRequest));
    }

}
