package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Address;

import java.util.List;

public interface IAddressService {
    Address create(Address address);
    void update(Address address);
    void delete(int addressId);
    Address getOne(int addressId);
    List<Address> getAll();
    List<Address> findByCustomer(int customerId);
}
