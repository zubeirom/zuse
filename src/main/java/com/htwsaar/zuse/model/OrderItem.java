package com.htwsaar.zuse.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * A class to safe an order item with
 *
 * @author Dominik Müller
 */
@Entity
@Table(name = "orderItem")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orderId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "articleId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Article article;
	private int quantity;
	@Column(name = "price", precision = 8, scale = 2)
	private double price;
	@CreationTimestamp
	private Timestamp createdAt;
	@CreationTimestamp
	private Timestamp updatedAt;


	/**
	 * Creates an Order Item which is part of a order
	 * @param order     id of the order
	 * @param article id of the article
	 * @param price     single price of the article
	 * @param quantity  quantity of the article
	 */
	public OrderItem(Order order, Article article, double price, int quantity) {
		this.order = order;
		this.article = article;
		this.price = price;
		this.quantity = quantity;
	}

	public OrderItem() {}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getId() {
		return id;
	}

	public Order getOrder() {
		return order;
	}

	public Article getArticle() {
		return article;
	}

	public int getQuantity() {
		return quantity;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return a string with informations about the order item
	 */
	@Override
	public String toString() {
		return "OrderItem{" +
				"id=" + id +
				", orderId=" + order +
				", Article=" + article +
				", quantity=" + quantity +
				", price=" + price +
				'}';
	}
}