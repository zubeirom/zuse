package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Commission;
import com.htwsaar.zuse.repository.CommissionRepository;
import com.htwsaar.zuse.repository.ICommissionRepository;

import java.util.List;

/**
 * Service to interact with an Commission DB object in your UI.
 * The Service provides every action that is supported
 */
public class CommissionService implements ICommissionService {

	/**
	 * Repository of the Service
	 */
	private final ICommissionRepository commissionRepository;

	/**
	 * Creates a new CommissionService. The appropriate repository is created within
	 */
	public CommissionService() {
		this.commissionRepository = new CommissionRepository();
	}

	/**
	 * Creates a new CommissionService with a given repository
	 * @param commissionRepository repository of service
	 */
	public CommissionService(ICommissionRepository commissionRepository) {
		this.commissionRepository = commissionRepository;
	}

	/**
	 * Creates a new Commission in the database
	 * 
	 * @param commission Commission to create in database
	 * @return the created Commission
	 */
	@Override
	public Commission create(Commission commission) {
		return this.commissionRepository.create(commission);
	}

	/**
	 * Updates a given Commission in the database
	 * 
	 * @param commission Commission to update in database
	 */
	@Override
	public void update(Commission commission) {
		this.commissionRepository.update(commission);
	}

	/**
	 * Deletes a Commission of the database
	 * 
	 * @param commissionId id of the Commission to delete
	 */
	@Override
	public void delete(int commissionId) {
		this.commissionRepository.delete(commissionId);
	}

	/**
	 * Gets one Commission from the database
	 * 
	 * @param commissionId id of Commission to get
	 * @return Commission which was retrieved
	 */
	@Override
	public Commission getOne(int commissionId) {
		return this.commissionRepository.getOne(commissionId);
	}

	/**
	 * Gets all Commission in the database
	 * 
	 * @return all Commission in the database
	 */
	@Override
	public List<Commission> getAll() {
		return this.commissionRepository.getAll();
	}
}
