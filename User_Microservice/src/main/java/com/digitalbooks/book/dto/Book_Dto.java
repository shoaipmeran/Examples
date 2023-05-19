package com.digitalbooks.book.dto;

import javax.persistence.*;


public class Book_Dto {
	
	private int book_id;
	
	
	
	private String title;

	private String category;

	
	private String logo;
	
	
	private String author;
	private String publisher;
	private String published_date;
	
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
	public Book_Dto(int book_id, String title, String category, String author, String publisher,
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
	
	public Book_Dto() {
		
	}
	
	
	
	

}
