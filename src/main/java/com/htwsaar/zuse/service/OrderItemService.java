package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.OrderItem;
import com.htwsaar.zuse.repository.IOrderItemRepository;
import com.htwsaar.zuse.repository.OrderItemRepository;

import java.util.List;

/**
 * Service to interact with an OrderItem DB object in your UI.
 * The Service provides every action that is supported
 */
public class OrderItemService implements IOrderItemService {

	/**
	 * Repository of the Service
	 */
	private final IOrderItemRepository orderItemRepository;

	/**
	 * Creates a new OrderItemService. The appropriate repository is created within
	 */
	public OrderItemService() {
		this.orderItemRepository = new OrderItemRepository();
	}

	/**
	 * Creates a new OrderItemService with a given Repository
	 * @param orderItemRepository repository of service
	 */
	public OrderItemService(IOrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	/**
	 * Creates a new OrderItem in the database
	 * 
	 * @param orderItem OrderItem to create in database
	 * @return the created OrderItem
	 */
	@Override
	public OrderItem create(OrderItem orderItem) {
		return this.orderItemRepository.create(orderItem);
	}

	/**
	 * Updates a given OrderItem in the database
	 * 
	 * @param orderItem OrderItem to update in database
	 */
	@Override
	public void update(OrderItem orderItem) {
		this.orderItemRepository.update(orderItem);
	}

	/**
	 * Deletes an OrderItem of the database
	 * 
	 * @param orderItemId id of the OrderItem to delete
	 */
	@Override
	public void delete(int orderItemId) {
		this.orderItemRepository.delete(orderItemId);
	}

	/**
	 * Gets one OrderItem from the database
	 * 
	 * @param orderItemId id of OrderItem to get
	 * @return OrderItem which was retrieved
	 */
	@Override
	public OrderItem getOne(int orderItemId) {
		return this.orderItemRepository.getOne(orderItemId);
	}

	/**
	 * Gets all OrderItems in the database
	 * 
	 * @return all OrderItems in the database
	 */
	@Override
	public List<OrderItem> getAll() {
		return this.orderItemRepository.getAll();
	}

	/**
	 * Finds all OrderItems in a Order 
	 * 
	 * @param orderId id of the Order
	 * @return all OrderItems within the Order  
	 */
	@Override
	public List<OrderItem> findByOrder(int orderId) {
		return this.orderItemRepository.findByOrder(orderId);
	}
}
