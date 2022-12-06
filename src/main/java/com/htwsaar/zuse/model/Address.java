package com.htwsaar.zuse.model;

import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Class to save clients addresses
 *
 * @author Dominik Müller
 */

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Customer customer;
	private String street;
	private String postalCode;
	private String city;
	private String country;
	@CreationTimestamp
	private Timestamp createdAt;
	@CreationTimestamp
	private Timestamp updatedAt;


	/**
	 * Creates an Object of an Customer address
	 *
	 * @param customer   Customer
	 * @param street     street of the customer
	 * @param postalCode postal code of the customer
	 * @param city       city of the costumer
	 * @param country    country of the customer
	 * @ TODO: 01.02.2022 Add ID Check methods when id Format is committed
	 */
	public Address(Customer customer, String street, String postalCode, String city, String country) {

		Util.checkForEmpty(street, "Please enter a street");
		Util.checkForEmpty(postalCode, "Please enter a postal code");
		Util.checkForEmpty(city, "Please enter a city");
		Util.checkForEmpty(country, "Please enter a country");

		this.customer = customer;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public Address() {}


	public void setStreet(String street) {
		this.street = street;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getStreet() {
		return street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
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

	/**
	 * Creates a valid String with everything needed to send the Order to the client
	 *
	 * @return Complete address
	 */

	public String toString() {

		String COMMA = ", ";
		return street + COMMA + postalCode + COMMA + city + COMMA + country;
	}

	public String toStringFancy(){
		String newline = "\n";
		return street + newline + postalCode + newline + city + newline + country;
	}
}
