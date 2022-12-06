package com.htwsaar.zuse.repository;

import com.htwsaar.zuse.model.OrderItem;

import java.util.List;

public interface IOrderItemRepository {
	OrderItem create(OrderItem orderItem);

	List<OrderItem> getAll();

	OrderItem getOne(int orderItemId);

	void update(OrderItem orderItem);

	void delete(int orderItemId);

	List<OrderItem> findByOrder(int orderId);
}
