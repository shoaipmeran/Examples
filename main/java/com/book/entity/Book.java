package com.book.entity;

import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int book_id;
	
	
	@Column(name = "title")
	private String title;

	@Column(name = "category")
	private String category;

	@Column(name = "logo")
	private String logo;
	
	@Column(name = "author")
	private String author;
	@Column(name = "publisher")
	private String publisher;
	@Column(name = "published_date")
	private String published_date;
	@Column(name = "content")
	private String content;
	@Column(name = "active")
	private boolean active;
	@Column(name = "price")
	private double price;
	
	@Column(name = "created_on")
	private String created_on;
	
	@Column(name = "author_id")
	private int author_id;
	
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublished_date() {
		return published_date;
	}
	public void setPublished_date(String published_date) {
		this.published_date = published_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Book(int book_id, String title, String category, String author, String publisher,
			String published_date, double price, String logo) {
		this.book_id = book_id;
		this.title = title;
		this.category = category;
		this.logo = logo;
		this.author = author;
		this.publisher = publisher;
		this.published_date = published_date;
		this.price = price;
	}
	
	public Book(int book_id, String title, String category, String logo, String author, String publisher,
			String published_date, String content, boolean active, double price, int author_id) {
		this.book_id = book_id;
		this.title = title;
		this.category = category;
		this.logo = logo;
		this.author = author;
		this.publisher = publisher;
		this.published_date = published_date;
		this.content = content;
		this.active = active;
		this.price = price;
		this.author_id = author_id;
	}
	
	public Book() {
		
	}
	public Book(String content) {
		this.content = content;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
	

}
