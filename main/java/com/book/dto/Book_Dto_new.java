package com.book.dto;

import javax.persistence.*;


public class Book_Dto_new {
	
	private int book_id;
	
	
	
	private String title;

	private String category;
	
	private int subscription_id;

	
	private String logo;
	
	
	private String author;
	private String publisher;
	private String published_date;
	
	private boolean active;
	
	private double price;
	
	
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
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Book_Dto_new(int book_id, String title, String category, String author, String publisher,
			String published_date, double price, String logo,boolean active,int subscription_id) {
		this.book_id = book_id;
		this.subscription_id=subscription_id;
		this.title = title;
		this.category = category;
		this.logo = logo;
		this.author = author;
		this.publisher = publisher;
		this.published_date = published_date;
		this.price = price;
		this.active = active;
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Book_Dto_new() {
		
	}
	public int getSubscription_id() {
		return subscription_id;
	}
	public void setSubscription_id(int subscription_id) {
		this.subscription_id = subscription_id;
	}
	
	
	
	

}
