package com.book.service;

import java.util.List;
import java.util.Optional;

import com.book.dto.Book_Dto;
import com.book.dto.Book_Dto_new;
import com.book.dto.Invoice_Details;
import com.book.entity.Book;
import com.book.entity.Subscribe;

public interface Reader_Service {

	int subscribeBook(Subscribe subs);

	List<Book_Dto> searchBookById(int subscribe_id, String emailid);

	List<Book_Dto_new> getAllsubscribeBooks(String email_id);

	Optional<Book> readBookById(int subs_id, String email_id);

	List<Subscribe> getAllsubscribeList(String email_id);

	List<Invoice_Details> getSubscribtionInvoice(String email_id, int subs_id);
	
	Optional<Subscribe> getsubscribeBook(String email_id,int bookid);

	Optional<Subscribe> getSubscribtionDetails(String email_id, int subs_id);
	
	
	
	
}
