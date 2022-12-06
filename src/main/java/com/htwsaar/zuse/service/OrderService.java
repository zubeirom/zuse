package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Order;
import com.htwsaar.zuse.repository.IOrderRepository;
import com.htwsaar.zuse.repository.OrderRepository;

import java.util.List;

/**
 * Service to interact with an Order DB object in your UI.
 * The Service provides every action that is supported
 */
public class OrderService implements IOrderService {

	/**
	 * Repository of the Service
	 */
	private final IOrderRepository orderRepository;

	/**
	 * Creates a new OrderService. The appropriate repository is created within
	 */
	public OrderService() {
		this.orderRepository = new OrderRepository();
	}

	/**
	 * Creates a new OrderService with a given Repository
	 * @param orderRepository repository of service
	 */
	public OrderService(IOrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	/**
	 * Creates a new Order in the database
	 * 
	 * @param order Order to create in database
	 * @return the created Order
	 */
	@Override
	public Order create(Order order) {
		return this.orderRepository.create(order);
	}

	/**
	 * Updates a given Order in the database
	 * 
	 * @param order Order to update in database
	 */
	@Override
	public void update(Order order) {
		this.orderRepository.update(order);
	}

	/**
	 * Deletes a Order of the database
	 * 
	 * @param orderId id of the Order to delete
	 */
	@Override
	public void delete(int orderId) {
		this.orderRepository.delete(orderId);
	}

	/**
	 * Gets one Order from the database
	 * 
	 * @param orderId id of Order to get
	 * @return Order which was retrieved
	 */
	@Override
	public Order getOne(int orderId) {
		return this.orderRepository.getOne(orderId);
	}

	/**
	 * Gets all Orders in the database
	 * 
	 * @return all Orders in the database
	 */
	@Override
	public List<Order> getAll() {
		return this.orderRepository.getAll();
	}
}
