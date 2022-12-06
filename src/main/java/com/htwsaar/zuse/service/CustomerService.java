package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.repository.CustomerRepository;
import com.htwsaar.zuse.repository.ICustomerRepository;
import java.util.List;

/**
 * Service to interact with an Customer DB object in your UI.
 * The Service provides every action that is supported
 */
public class CustomerService implements ICustomerService {

	/**
	 * Repository of the Service
	 */
	private final ICustomerRepository customerRepository;

	/**
	 * Creates a new CustomerService. The appropriate repository is created within
	 */
	public CustomerService() {
		this.customerRepository = new CustomerRepository();
	}

	/**
	 * Creates a new CustomerService with a given Repository
	 * @param customerRepository repository of service
	 */
	public CustomerService(ICustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	/**
	 * Creates a new Customer in the database
	 * 
	 * @param customer Customer to create in database
	 * @return the created Customer
	 */
	@Override
	public Customer create(Customer customer) {
		return this.customerRepository.create(customer);
	}

	/**
	 * Updates a given Customer in the database
	 * 
	 * @param customer Customer to update in database
	 */
	@Override
	public void update(Customer customer) {
		this.customerRepository.update(customer);
	}

	/**
	 * Deletes a Customer of the database
	 * 
	 * @param customerId id of the Customer to delete
	 */
	@Override
	public void delete(int customerId) {
		this.customerRepository.delete(customerId);
	}

	/**
	 * Gets one Customer from the database
	 * 
	 * @param customerId id of Customer to get
	 * @return Customer which was retrieved
	 */
	@Override
	public Customer getOne(int customerId) {
		return this.customerRepository.getOne(customerId);
	}

	/**
	 * Gets all Customers in the database
	 * 
	 * @return all Customers in the database
	 */
	@Override
	public List<Customer> getAll() {
		return this.customerRepository.getAll();
	}
}
