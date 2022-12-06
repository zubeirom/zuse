package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.repository.IWarehouseRepository;
import com.htwsaar.zuse.repository.WarehouseRepository;

import java.util.List;

/**
 * Service to interact with an Article DB object in your UI.
 * The Service provides every action that is supported
 */
public class WarehouseService implements IWarehouseService {

	/**
	 * Repository of the Service
	 */
    private final IWarehouseRepository warehouseRepository;

	/**
	 * Creates a new WarehouseService. The appropriate repository is created within
	 */
    public WarehouseService() {
        this.warehouseRepository = new WarehouseRepository();
    }

	/**
	 * Creates a new WarehouseService with a given Repository
	 * @param warehouseRepository repository of service
	 */
    public WarehouseService(IWarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

	/**
	 * Creates a new Warehouse in the database
	 * 
	 * @param warehouse Warehouse to create in database
	 * @return the created Warehouse
	 */
    @Override
    public Warehouse create(Warehouse warehouse) {
        return this.warehouseRepository.create(warehouse);
    }

	/**
	 * Updates a given Warehouse in the database
	 * 
	 * @param warehouse Warehouse to update in database
	 */
    @Override
    public void update(Warehouse warehouse) {
        this.warehouseRepository.update(warehouse);
    }
    
	/**
	 * Deletes an Warehouse of the database
	 * 
	 * @param warehouseId id of the Warehouse to delete
	 */
    @Override
    public void delete(int warehouseId) {
        this.warehouseRepository.delete(warehouseId);
    }

	/**
	 * Gets one Warehouse from the database
	 * 
	 * @param warehouseId id of Warehouse to get
	 * @return Warehouse which was retrieved
	 */
    @Override
    public Warehouse getOne(int warehouseId) {
        return this.warehouseRepository.getOne(warehouseId);
    }

	/**
	 * Gets all Warehouses in the database
	 * 
	 * @return all Warehouses in the database
	 */
    @Override
    public List<Warehouse> getAll() {
        return this.warehouseRepository.getAll();
    }
}
