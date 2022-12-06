package com.htwsaar.zuse;

import com.htwsaar.zuse.model.*;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class OutgoingTest {
    ICustomerRepository customerRepository = new CustomerRepository();
    IAddressRepository addressRepository = new AddressRepository();
    ICommissionRepository commissionRepository = new CommissionRepository();
    IOutgoingRepository outgoingRepository = new OutgoingRepository();

    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM outgoing").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM commission").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM address").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        Outgoing outgoing = sampleOutgoing();
        Assertions.assertTrue(outgoing.getId() > 0);
    }

    @Test
    public void testUpdate() {
        Outgoing outgoing = sampleOutgoing();
        outgoing.setStatus(Status.ARRIVED);
        outgoingRepository.update(outgoing);
        Outgoing updated = outgoingRepository.getOne(outgoing.getId());
        Assertions.assertEquals(outgoing.getStatus(), updated.getStatus());
    }

    @Test
    public void testGetOne() {
        Outgoing outgoing = sampleOutgoing();
        Outgoing fetched = outgoingRepository.getOne(outgoing.getId());
        Assertions.assertEquals(outgoing.getId(), fetched.getId());
    }

    @Test
    public void testGetAll() {
        List<Outgoing> outgoings = outgoingRepository.getAll();
        Assertions.assertFalse(outgoings.isEmpty());
    }

    @Test
    public void testDelete() {
        Outgoing outgoing = sampleOutgoing();
        outgoingRepository.delete(outgoing.getId());
        Outgoing fetched = outgoingRepository.getOne(outgoing.getId());
        Assertions.assertNull(fetched);
    }

    private Outgoing sampleOutgoing() {
        Customer customer = customerRepository.create(new Customer("TerraX", "Max",
                "Mustermann", "Mustermail@muster.com", "+496666666"));
        Address address = addressRepository.create(new Address(customer,
                "StreetOne 21", "676767", "SimCity",
                "Germany"));
        Commission commission = commissionRepository.create(new Commission(address));
        return outgoingRepository.create(new Outgoing(commission, address, Status.READY));
    }
}
