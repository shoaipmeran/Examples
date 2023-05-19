package com.book.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subscribe")
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscription_id")
	private int subscription_id;
	
	@Column(name = "book_id")
	private int book_id;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "email_id")
	private String email_id;
	
	@Column(name = "is_cancelled")
	private boolean is_cancelled;
	
	

	public boolean isIs_cancelled() {
		return is_cancelled;
	}

	public void setIs_cancelled(boolean is_cancelled) {
		this.is_cancelled = is_cancelled;
	}

	public int getSubscription_id() {
		return subscription_id;
	}

	public void setSubscription_id(int subscription_id) {
		this.subscription_id = subscription_id;
	}

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

	public void setDate(String string) {
		this.date = string;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
	

}
