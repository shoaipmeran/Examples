package com.book.service;

import java.util.List;
import java.util.Optional;

import com.book.dto.Book_Dto;
import com.book.entity.Book;
import com.book.entity.Subscribe;

public interface Book_Service {

	int saveBook(Book books);

	List<Book_Dto> getAllBooks();

	List<Book_Dto> searchBookByTitle(String title);

	Optional<Book> searchBookByAuthorAndBookId(int book_id, int author_id);
	
	List<Book_Dto> searchBookByFilters(String category,String title,String author,double price,String publisher);

	List<Book> searchBookByAuthorId(int author_id);
	
	List<Book_Dto> searchBookByAuthorIdAndActive(int author_id,boolean block);
	Optional<Book> getbookbyid(int bookid);

	List<Book_Dto> searchBookByAllFilters(String category, String title, String author, double price, String publisher,String created_on);	
	
	
}
