package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.repository.AddressRepository;
import com.htwsaar.zuse.repository.IAddressRepository;

import java.util.List;

/**
 * Service to interact with an Address DB object in your UI. The Service
 * provides every action that is supported
 */
public class AddressService implements IAddressService {

	private final IAddressRepository addressRepository;

	/**
	 * Creates a new AddressService. The appropriate Repository is created within
	 */
	public AddressService() {
		this.addressRepository = new AddressRepository();
	}

	/**
	 * Creates a new AddressService with a given Repository
	 * @param addressRepository repository of service
	 */
	public AddressService(IAddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	/**
	 * Creates a new Address in the database
	 * 
	 * @param address Address to create in database
	 * @return the created Address 
	 */
	@Override
	public Address create(Address address) {
		return this.addressRepository.create(address);
	}

	/**
	 * Updates a given Address in the database
	 * 
	 * @param address Address to update in database
	 */
	@Override
	public void update(Address address) {
		this.addressRepository.update(address);
	}

	/**
	 * Deletes an Address of the database
	 * 
	 * @param addressID id of the Address to delete
	 */
	@Override
	public void delete(int addressID) {
		this.addressRepository.delete(addressID);
	}

	/**
	 * Gets one Address from the database
	 * 
	 * @param addressId id of Address to get
	 * @return Address which was retrieved
	 */
	@Override
	public Address getOne(int addressId) {
		return this.addressRepository.getOne(addressId);
	}

	/**
	 * Gets all Addresses in the database
	 * 
	 * @return all Addresses in the database
	 */
	@Override
	public List<Address> getAll() {
		return this.addressRepository.getAll();
	}

	/**
	 * Finds all Addresses of a Customer
	 * 
	 * @param customerId id of Customer
	 * @return all Addresses related to the Customer
	 */
	@Override
	public List<Address> findByCustomer(int customerId) {
		return this.addressRepository.findByCustomer(customerId);
	}
}
