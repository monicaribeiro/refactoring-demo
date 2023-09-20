package com.demo.refactoring.repository;

import org.springframework.stereotype.Component;

@Component
public class CustomerRepository {

    public String findById(String id) {
        return "Mock - customer found.";
    }

}
