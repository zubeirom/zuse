package com.htwsaar.zuse.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Class to save orders
 */
@Entity
@Table(name = "`order`")
public class Order {
	public enum OrderStatus{incoming, commission, outgoing}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addressId")
	private Address address;
	private OrderStatus status;
	@CreationTimestamp
	private Timestamp createdAt;
	@CreationTimestamp
	private Timestamp updatedAt;

	public Order(OrderStatus status) {
		this.status = status;
	}

	public Order(Address address){
		this.address = address;
		status = OrderStatus.incoming;
	}

	public Order() {}


	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Customer getCustomer(){
		return address.getCustomer();
	}

	public static int compare(Order o1, Order o2){
		int customerComparison = Customer.compare(o1.getCustomer(), o2.getCustomer());
		if (customerComparison != 0){
			return customerComparison;
		} else {
			return Integer.compare(o1.getId(), o2.getId());
		}
	}
}
