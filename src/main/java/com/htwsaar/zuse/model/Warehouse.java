package com.htwsaar.zuse.model;

import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import jakarta.persistence.*;

/**
 * Class to save a warehouse
 */
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @CreationTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;
    private String category;

    public Warehouse (int id, String name, Timestamp createdAt, String category) {
        this(id, name, createdAt);
        this.category = category;
    }

    public Warehouse (int id, String name, Timestamp createdAt){
        this(id, name);
        this.createdAt = createdAt;
    }

    public Warehouse(int id, String name) {
        Util.checkForEmpty(name, "Please enter a name for warehouse");
        this.name = name;
        Util.check(id > 0, "ID must be greater than 0");
        this.id = id;
        this.createdAt = Timestamp.from(Instant.now());


    }

    public Warehouse(String name) {
        Util.checkForEmpty(name, "Please enter a name for warehouse");
        this.name = name;
        this.createdAt = Timestamp.from(Instant.now());
    }

    public Warehouse() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCategory(String newCategory) {
        category = newCategory;
    }

    public String getCategory() {
        return category;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Warehouse)) {
            return false;
        }
        Warehouse tested = (Warehouse) o;
        return (tested.getId()==this.id &&
                tested.getName() == this.name &&
                tested.getCreatedAt() == this.createdAt);
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
