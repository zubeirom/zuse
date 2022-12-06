package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Customer;

import java.util.List;

public interface ICustomerRepository {
    Customer create(Customer createCustomerDto);
    List<Customer> getAll();
    Customer getOne(int customerId);
    Customer update(Customer customer);
    void delete(int customerId);
}
