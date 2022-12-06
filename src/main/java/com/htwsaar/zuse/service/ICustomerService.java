package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer create(Customer createCustomerDto);
    void update(Customer customer);
    void delete(int customerId);
    Customer getOne(int customerId);
    List<Customer> getAll();
}
