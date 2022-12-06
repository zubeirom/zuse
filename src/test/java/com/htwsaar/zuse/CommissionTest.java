package com.htwsaar.zuse;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Commission;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CommissionTest {
    ICustomerRepository customerRepository = new CustomerRepository();
    IAddressRepository addressRepository = new AddressRepository();
    ICommissionRepository commissionRepository = new CommissionRepository();

    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM commission").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM address").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        Commission commission = sampleCommission();
        Assertions.assertTrue(commission.getId() > 0);
    }

    @Test
    public void testUpdate() {
        Commission commission = sampleCommission();
        commission.getAddress().setStreet("LALA");
        commissionRepository.update(commission);
        Commission getUpdated = commissionRepository.getOne(commission.getId());
        Assertions.assertEquals(getUpdated.getAddress().getStreet(), getUpdated.getAddress().getStreet());
    }

    @Test
    public void testGetOne() {
        Commission commission = sampleCommission();
        Commission fetched = commissionRepository.getOne(commission.getId());
        Assertions.assertNotNull(fetched);
    }

    @Test
    public void testGetAll() {
        List<Commission> commissions = commissionRepository.getAll();
        Assertions.assertFalse(commissions.isEmpty());
    }

    @Test
    public void testDelete() {
        Commission commission = sampleCommission();
        commissionRepository.delete(commission.getId());
        Commission fetched = commissionRepository.getOne(commission.getId());
        Assertions.assertNull(fetched);
    }

    private Commission sampleCommission() {
        Customer customer = customerRepository.create(new Customer("TerraX", "Max",
                "Mustermann", "Mustermail@muster.com", "+496666666"));
        Address address = addressRepository.create(new Address(customer,
                "StreetOne 21", "676767", "SimCity",
                "Germany"));
        return commissionRepository.create(new Commission(address));
    }
}
