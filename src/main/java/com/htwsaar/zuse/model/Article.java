package com.htwsaar.zuse.model;


import com.htwsaar.zuse.util.Util;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import jakarta.persistence.*;

/**
 * A class to save informations about an article like name, category, price, quantity etc.
 *
 * @author Dominik Müller
 */
@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "warehouseId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Warehouse warehouse;
	private String articleName;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;
	@Column(name = "price", precision = 8, scale = 2)
	private double price;
	private int stock;
	@CreationTimestamp
	private Timestamp createdAt;
	@CreationTimestamp
	private Timestamp updatedAt;

	/**
	 * Creates an article object
	 *
	 * @param warehouse   Instance of the warehouse in which the article is stored
	 * @param name        name of the aricle
	 * @param description (optional) description of the article
	 * @param price       price of the article
	 * @param stock       quantity of the article
	 */
	public Article(Warehouse warehouse, String name, String description, Category category, double price, int stock) {

		Util.checkForEmpty(name, "Please enter a name for the article");

		this.warehouse = warehouse;
		this.articleName = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.stock = stock;
	}

	/**
	 * Creates an article object
	 *
	 * @param warehouse   Instance of the warehouse in which the article is stored
	 * @param name        name of the aricle
	 * @param description (optional) description of the article
	 * @param price       price of the article
	 * @param stock       quantity of the article
	 */
	public Article(Warehouse warehouse, String name, String description, double price, int stock) {

		Util.checkForEmpty(name, "Please enter a name for the article");

		this.warehouse = warehouse;
		this.articleName = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	/**
	 * Creates an article object
	 *
	 * @param id          Id of the article
	 * @param warehouse   Instance of the warehouse in which the article is stored
	 * @param name        name of the aricle
	 * @param categoryId  Category id of the article
	 * @param price       price of the article
	 * @param stock       quantity of the article
	 */
	public Article(int id, Warehouse warehouse, String name, int categoryId, double price, int stock, Timestamp createdAt) {

		Util.checkForEmpty(name, "Please enter a name for the article");

		this.id = id;
		this.warehouse = warehouse;
		this.articleName = name;
		this.price = price;
		this.stock = stock;

		this.createdAt = createdAt;
	}

	public Article() {}

	public int getId() {
		return id;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public String getArticleName() {
		return articleName;
	}

	public String getDescription() {
		return description;
	}

	public Category getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
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

	public void setCategory(Category category){
		this.category = category;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public static int compare(Article o1, Article o2){
		if (o1.getArticleName().equals(o2.getArticleName())){
			return o1.getArticleName().compareTo(o2.getArticleName());
		} else {
			return Integer.compare(o1.getId(), o2.getId());
		}

	}

	@Override
	public boolean equals(Object o){
		if (o instanceof Article){
			Article otherArticle = (Article) o;
			return this.getArticleName().equals(otherArticle.getArticleName())&&
					this.getId() == otherArticle.getId();
		} else {
			return false;
		}
	}

	/**
	 * Creates a string with article name, category id, price and stock
	 *
	 * @return string with article name, category id, price and stock
	 */
	public String toStringFancy() {
		return "articleName='" + articleName + '\'' +
				", categoryId=" + category +
				", price=" + price +
				", stock=" + stock;
	}

	public String toString(){
		return articleName + " (" + id + ")";
	}
}
