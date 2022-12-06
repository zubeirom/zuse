package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Warehouse;

import java.util.List;

public interface IWarehouseRepository {
    Warehouse create(Warehouse warehouse);
    List<Warehouse> getAll();
    Warehouse getOne(int warehouseId);
    void update(Warehouse warehouse);
    void delete(int warehouseId);
}
