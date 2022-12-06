package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Warehouse;

import java.util.List;

public interface IWarehouseService {
    Warehouse create(Warehouse warehouse);
    void update(Warehouse warehouse);
    Warehouse getOne(int warehouseId);
    List<Warehouse> getAll();
    void delete(int warehouseId);
}
