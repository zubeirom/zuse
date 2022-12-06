package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.OrderItem;

import java.util.List;

public interface IOrderItemService {
	OrderItem create(OrderItem orderItem);

	void update(OrderItem orderItem);

	void delete(int orderItemId);

	OrderItem getOne(int orderItemId);

	List<OrderItem> getAll();

	List<OrderItem> findByOrder(int orderId);
}
