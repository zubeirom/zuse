package com.htwsaar.zuse.model;



import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

import java.sql.Timestamp;



/**
 *Class for making Commissions
 *
 *@author Sven Goergen
 *
 */

@Entity
@Table(name = "commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;
    @CreationTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;

    /**
     * Constructor with id, orderId, addressId;
     *
     * @param address: identification-number for every address
     */
    public Commission(Address address){
        this.address=address;
    }

    public Commission() {}

    public int getId(){
        return id;
    }
    public Timestamp getCreatedAt(){
        return createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Address getAddress(){
        return address;
    }
    /**
     *
     * @param address: identification-number for every address
     */
    public void setAddress(Address address){
        this.address=address;
    }
}
