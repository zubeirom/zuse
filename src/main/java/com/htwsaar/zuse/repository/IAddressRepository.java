package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Address;

import java.util.List;

public interface IAddressRepository {
    Address create(Address address);
    List<Address> getAll();
    Address getOne(int addressId);
    void update(Address address);
    void delete(int addressId);
    List<Address> findByCustomer(int customerId);
}
