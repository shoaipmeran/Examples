package com.book.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dto.Book_Dto;
import com.book.dto.Book_Dto_new;
import com.book.dto.Invoice_Details;
import com.book.entity.Book;
import com.book.entity.Subscribe;
import com.book.repository.Reader_Repository;

@Service
public class Reader_Service_Impl implements Reader_Service{

	@Autowired
	Reader_Repository reader_repo;
	
	@Override
	public int subscribeBook(Subscribe subs) {
		Subscribe sub=reader_repo.save(subs);
		return sub.getSubscription_id();
	}

	

	@Override
	public List<Book_Dto> searchBookById(int subscribe_id, String emailid) {
		List<Book> book= reader_repo.findBookByEmailAndSubscribeId(subscribe_id,emailid,false);
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:book) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getAuthor(),bokk.getCategory(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}
		return new_list;
	}



	@Override
	public List<Book_Dto_new> getAllsubscribeBooks(String email_id) {
		List<Book> book= reader_repo.findBookByEmail(email_id,false);
		
		List<Book_Dto_new> new_list=new ArrayList<Book_Dto_new>();
		for(Book bokk:book) {
			List<Subscribe> subsid= reader_repo.findBookByEmails(bokk.getBook_id(),email_id);
			System.out.println(subsid.get(0).getSubscription_id());
			new_list.add(new Book_Dto_new(bokk.getBook_id(),bokk.getTitle(),bokk.getAuthor(),bokk.getCategory(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive(),subsid.get(0).getSubscription_id()));
		}
		return new_list;
	}



	@Override
	public Optional<Book> readBookById(int subs_id, String email_id) {
		return reader_repo.readBookByEmailAndSubscribeId(subs_id,email_id,true,false);
	}



	@Override
	public List<Subscribe> getAllsubscribeList(String email_id) {
		return reader_repo.getSubscriptionListByEmail(email_id,false);
	}



	@Override
	public List<Invoice_Details> getSubscribtionInvoice(String email_id, int subs_id) {
		List<Subscribe> su= reader_repo.getSubscriptionInvoiceByEmail(email_id,subs_id,false);
		
		List<Invoice_Details> new_list=new ArrayList<Invoice_Details>();
		System.out.println("tesnewwwt");
		for(Subscribe s:su) {
			System.out.println(s.getBook_id());
			String milliSec = s.getDate();

			// Creating date format
			DateFormat simple = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			// Creating date from milliseconds
			// using Date() constructor
			Date start_date = new Date(Long.parseLong(milliSec));

			// Formatting Date according to the
			// given format
			System.out.println(simple.format(start_date));
			new_list.add(new Invoice_Details(s.getBook_id(),s.getPrice(),simple.format(start_date)));
		}
		return new_list;
	}



	@Override
	public Optional<Subscribe> getsubscribeBook(String email_id, int bookid) {
		return reader_repo.getSubscriptionBook(email_id,bookid,false);
	}



	@Override
	public Optional<Subscribe> getSubscribtionDetails(String email_id, int subs_id) {
		return reader_repo.getSubscribeBook(email_id,subs_id,false);
	}
}
