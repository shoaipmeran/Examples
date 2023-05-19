package com.book.dto;

public class Invoice_Details {

	
	private int book_id;
	private double price;
	private String date;
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Invoice_Details(int book_id, double price, String date) {
		this.book_id = book_id;
		this.price = price;
		this.date = date;
	}
	
}
