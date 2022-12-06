package com.htwsaar.zuse;

import com.htwsaar.zuse.model.*;
import com.htwsaar.zuse.repository.*;
import com.htwsaar.zuse.util.JPAUtil;
import com.htwsaar.zuse.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class OrderItemTest {
    ICustomerRepository customerRepository = new CustomerRepository();
    IAddressRepository addressRepository = new AddressRepository();
    ICommissionRepository commissionRepository = new CommissionRepository();
    IOrderRepository orderRepository = new OrderRepository();
    IOrderItemRepository orderItemRepository = new OrderItemRepository();
    IWarehouseRepository warehouseRepository = new WarehouseRepository();
    ICategoryRepository categoryRepository = new CategoryRepository();
    IArticleRepository articleRepository = new ArticleRepository();


    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM orderItem").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM `order`").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM commission").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM address").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM customer").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM article").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM warehouse").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM category").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        OrderItem orderItem = createDummy();
        Assertions.assertTrue(orderItem.getId() > 0);
    }

    @Test
    public void testUpdate() {
        OrderItem orderItem = createDummy();
        orderItem.setQuantity(15);
        orderItemRepository.update(orderItem);
        OrderItem updated = orderItemRepository.getOne(orderItem.getId());
        Assertions.assertEquals(15, updated.getQuantity());
    }

    @Test
    public void testGetOne() {
        OrderItem orderItem = createDummy();
        OrderItem o2 = orderItemRepository.getOne(orderItem.getId());
        Assertions.assertEquals(orderItem.getId(), o2.getId());
    }

    @Test
    public void testGetAll() {
        List<OrderItem> orderItems = orderItemRepository.getAll();
        Assertions.assertFalse(orderItems.isEmpty());
    }

    @Test
    public void testDelete() {
        OrderItem orderItem = createDummy();
        orderItemRepository.delete(orderItem.getId());
        OrderItem o2 = orderItemRepository.getOne(orderItem.getId());
        Assertions.assertNull(o2);
    }

    private OrderItem createDummy() {
        Customer customer = customerRepository.create(new Customer("Amazon", "Jeff", "Bezos", "jeffbezos@mail.com", "+00000000000"));
        Address address = addressRepository.create(new Address(customer, "45th Street", "34793", "Seattle", "USA"));
        Commission commission = commissionRepository.create(new Commission(address));
        Order order = orderRepository.create(new Order(address));
        Warehouse warehouse = warehouseRepository.create(new Warehouse("Amazon"));
        Category category = categoryRepository.create(new Category(Util.generateRandomString()));
        Article article = articleRepository.create(new Article(warehouse, "JBL HS", "DESC", category, 29.99, 40));
        return orderItemRepository.create(new OrderItem(order, article, 29.99, 10));
    }
}
