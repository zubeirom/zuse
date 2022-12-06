package com.htwsaar.zuse.model;

import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Class to save a customer
 */
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @CreationTimestamp
    private Timestamp createdAt;
    @CreationTimestamp
    private Timestamp updatedAt;

    /**
     * Creates an Customer Object with
     * @param companyName name of company
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param email email of the customer
     * @param phone phone number of the customer
     */
    public Customer(String companyName, String firstName, String lastName, String email, String phone) {
        Util.checkForEmpty(companyName, "Not complete or invalid ");
        Util.checkForEmpty(firstName, "Not complete or invalid ");
        Util.checkForEmpty(lastName, "Not complete or invalid ");
        Util.checkForEmpty(phone, "Not complete or invalid ");
        Util.checkForEmpty(email, "Not complete or invalid ");

        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Customer() {}

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static int compare(Customer o1, Customer o2){
        if (o1.getLastName().compareTo(o2.getLastName()) != 0){
            return o1.getLastName().compareTo(o2.getLastName());
        } else if (o1.getFirstName().compareTo(o2.getFirstName()) != 0) {
            o1.getFirstName().compareTo(o2.getFirstName());
        } else {
            return o1.getCompanyName().compareTo(o2.getCompanyName());
        }
        return 0;
    }

    @Override
    public String toString() {
        return lastName+", "+firstName+" ("+id+")";
    }
}
