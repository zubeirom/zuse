package com.htwsaar.zuse.model;

import jakarta.persistence.*;


import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

/**
 * Class for making Outgoing orders
 *
 * @author Yanik Sch�tzle
 */

@Entity
@Table(name = "outgoing")
public class Outgoing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commissionId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Commission commission;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;
    @CreationTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;

    /**
     * Constructor with id, orderId, customerId;
     *
     * @param id:           identification-number for every outgoing order
     * @param commission: identification-number for every commission
     * @param address:   identification-number for every Customer
     * @param status        :		  status of the outgoing order
     */
    public Outgoing(int id, Commission commission, Address address, Status status) {
        Util.check(id > 0, "ID cant be negative or 0");
        this.id = id;
        this.commission = commission;
        this.address = address;
        this.status = status;

    }

    /**
     * Constructor with id, orderId, customerId;
     *
     * @param commission: identification-number for every commission
     * @param address:   identification-number for every Customer
     * @param status        :		  status of the outgoing order
     */
    public Outgoing(Commission commission, Address address, Status status) {
        this.commission = commission;
        this.address = address;
        this.status = status;
    }

    public Outgoing() {}

    public int getId() {
        return id;
    }

    public Commission getCommission() {
        return commission;
    }

    public Address getCustomer() {
        return address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Changes the status of the outgoing order
     *
     * @param status:    status of the outgoing order
     * @param updatedAt: time of the update
     */
    public void changeStatus(Status status, Timestamp updatedAt) {
        this.status = status;
        this.updatedAt = updatedAt;
    }

}
