package com.htwsaar.zuse;

import com.htwsaar.zuse.model.Warehouse;
import com.htwsaar.zuse.repository.IWarehouseRepository;
import com.htwsaar.zuse.repository.WarehouseRepository;
import com.htwsaar.zuse.util.JPAUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;

public class WarehouseTest {

    private final IWarehouseRepository warehouseRepository = new WarehouseRepository();

    @AfterAll
    public static void cleanUp() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery("DELETE FROM warehouse").executeUpdate();
        entityTransaction.commit();
        entityManager.close();
    }

    @Test
    public void testCreate() {
        Warehouse newWarehouse = warehouseRepository.create(new Warehouse("Zalando"));
        Assertions.assertTrue(newWarehouse.getId() > 0);
    }

    @Test
    public void testGetAll() {
        List<Warehouse> warehouses = warehouseRepository.getAll();
        Assertions.assertFalse(warehouses.isEmpty());
    }

    @Test
    public void testUpdate() {
        String newName = "Kaufland";
        Warehouse newWarehouse = warehouseRepository.create(new Warehouse("Amazon"));
        newWarehouse.setName(newName);
        warehouseRepository.update(newWarehouse);
        Warehouse getUpdated = warehouseRepository.getOne(newWarehouse.getId());
        Assertions.assertEquals(newName, getUpdated.getName());
    }

    @Test
    public void testGetOne() {
        Warehouse w1 = new Warehouse("Amazon");
        Warehouse newWarehouse = warehouseRepository.create(w1);
        Warehouse warehouse = warehouseRepository.getOne(newWarehouse.getId());
        Assertions.assertNotNull(warehouse.getName());
    }

    @Test
    public void testDelete() {
        Warehouse newWarehouse = warehouseRepository.create(new Warehouse("Amazon"));
        warehouseRepository.delete(newWarehouse.getId());
        Warehouse w1 = warehouseRepository.getOne(newWarehouse.getId());
        Assertions.assertNull(w1);
    }

}
