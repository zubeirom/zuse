package com.htwsaar.zuse;

import com.htwsaar.zuse.model.Address;
import com.htwsaar.zuse.model.Commission;
import com.htwsaar.zuse.model.Customer;
import com.htwsaar.zuse.model.Order;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class OrderTest {

    ICustomerRepository customerRepository = new CustomerRepository();
    IAddressRepository addressRepository = new AddressRepository();
    ICommissionRepository commissionRepository = new CommissionRepository();
    IOrderRepository orderRepository = new OrderRepository();

    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM `order`").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM commission").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM address").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        Order o = createDummy();
        Assertions.assertTrue(o.getId() > 0);
    }

    @Test
    public void testGetOne() {
        Order o = createDummy();
        Order fetched = orderRepository.getOne(o.getId());
        Assertions.assertEquals(o.getId(), fetched.getId());
    }

    @Test
    public void testGetAll() {
        List<Order> orders = orderRepository.getAll();
        Assertions.assertFalse(orders.isEmpty());
    }

    @Test
    public void testDelete() {
        Order o = createDummy();
        orderRepository.delete(o.getId());
        Order o2 = orderRepository.getOne(o.getId());
        Assertions.assertNull(o2);
    }

    private Order createDummy() {
        Customer bezos = customerRepository.create(new Customer("AWS", "Jeff", "Bezos", "bezos@aws.com", "+00000000"));
        Address address = addressRepository.create(new Address(bezos, "45th Street", "23489", "NYC", "US"));
        Commission commission = commissionRepository.create(new Commission(address));
        return orderRepository.create(new Order(address));
    }
}
