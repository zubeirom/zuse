package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.Order;

import java.util.List;

public interface IOrderRepository {
    Order create(Order order);
    List<Order> getAll();
    Order getOne(int orderId);
    void update(Order order);
    void delete(int orderId);
}
