package com.htwsaar.zuse.model;

import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 *Class for making Categories
 *
 *@author Sven Goergen
 *
 */

@Entity
@Table(name = "category",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name"}))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String name;
    @CreationTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;


    /**
     *  Constructor with name
     * @param name:      name of the category
     */

    public Category(String name){
        Util.check(name.length()>0,"Name cant be empty");
        this.name=name;
    }

    public Category() {}

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Timestamp getCreatedAt(){
        return createdAt;
    }
    public Timestamp getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
