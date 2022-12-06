package com.htwsaar.zuse.service;

import com.htwsaar.zuse.model.Outgoing;
import com.htwsaar.zuse.repository.IOutgoingRepository;
import com.htwsaar.zuse.repository.OutgoingRepository;

import java.util.List;

/**
 * Service to interact with an Outgoing DB object in your UI.
 * The Service provides every action that is supported
 */
public class OutgoingService implements IOutgoingService {
	
	/**
	 * Repository of the Service
	 */
	private final IOutgoingRepository outgoingRepository;
	
	/**
	 * Creates a new OutgoingService. The appropriate repository is created within
	 */
	public OutgoingService() {
		this.outgoingRepository = new OutgoingRepository();
	}
	
	/**
	 * Creates a new OutgoingService with a given Repository
	 * @param outgoingRepository repository of service
	 */
	public OutgoingService(IOutgoingRepository outgoingRepository) {
		this.outgoingRepository =outgoingRepository;
	}

	/**
	 * Creates a new Outgoing in the database
	 * 
	 * @param outgoing Outgoing to create in database
	 * @return the created Outgoing
	 */
    public Outgoing create(Outgoing outgoing) {
    	return this.outgoingRepository.create(outgoing);
    }

	/**
	 * Updates a given Outgoing in the database
	 * 
	 * @param outgoing Outgoing to update in database
	 */
    public void update(Outgoing outgoing) {
    	this.outgoingRepository.update(outgoing);
    }

	/**
	 * Deletes a Outgoing of the database
	 * 
	 * @param outgoingId id of the Outgoing to delete
	 */
    public void delete(int outgoingId) {
    	this.outgoingRepository.delete(outgoingId);
    }

	/**
	 * Gets one Outgoing from the database
	 * 
	 * @param outgoingId id of Outgoing to get
	 * @return Outgoing which was retrieved
	 */
    @Override
    public Outgoing getOne(int outgoingId) {
        return this.outgoingRepository.getOne(outgoingId);
    }

	/**
	 * Gets all Outgoings in the database
	 * 
	 * @return all Outgoings in the database
	 */
    @Override
    public List<Outgoing> getAll() {
        return this.outgoingRepository.getAll();
    }
}
