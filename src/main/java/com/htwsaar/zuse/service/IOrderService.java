package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Order;

import java.util.List;

public interface IOrderService {
    Order create(Order order);
    void update(Order order);
    void delete(int orderId);
    Order getOne(int orderId);
    List<Order> getAll();
}
