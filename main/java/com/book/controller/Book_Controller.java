package com.book.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.dto.Book_Dto;
import com.book.dto.Book_Dto_new;
import com.book.dto.Content;
import com.book.dto.Invoice_Details;
import com.book.entity.Book;
import com.book.entity.Subscribe;
import com.book.repository.Book_Repository;
import com.book.service.Book_Service;
import com.book.service.Reader_Service;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/digitalbooks")
public class Book_Controller {


	@Autowired
	Book_Service book_service;
	
	@Autowired
	Reader_Service reader_service;
	
	@Autowired
	Book_Repository bb;

	@PostMapping("/author/{authorid}/books")
	public ResponseEntity<String> createBook(@PathVariable(value="authorid") String authorid,@RequestBody Book books) {

		try {
			books.setAuthor_id(Integer.parseInt(authorid));
			//books.setActive(true);
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar obj = Calendar.getInstance();
			String str = formatter.format(obj.getTime());
			System.out.println("Current Date: "+str );
			books.setCreated_on(str);
			int book_id=book_service.saveBook(books);
			return new ResponseEntity<String>("book added succesfully with id: "+book_id, HttpStatus.CREATED);
		} catch (Exception e) {
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@PutMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> editBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestBody Book books) {
		try {
			Optional<Book> book=book_service.searchBookByAuthorAndBookId(Integer.parseInt(book_id),Integer.parseInt(author_id));
			if(book.isPresent()) {
				book.get().setContent(books.getContent());
				book.get().setActive(books.isActive());
				book.get().setAuthor(books.getAuthor());
				book.get().setCategory(books.getCategory());
				book.get().setLogo(books.getLogo());
				book.get().setPublished_date(books.getPublished_date());
				book.get().setPrice(books.getPrice());
				book.get().setPublisher(books.getPublisher());
				book.get().setTitle(books.getTitle());
				book_service.saveBook(book.get());
				return new ResponseEntity<String>("book updated succesffuly..", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
	}
	
	@PostMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> blockunblockBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestParam String block) {
		try {
			Optional<Book> book=book_service.searchBookByAuthorAndBookId(Integer.parseInt(book_id),Integer.parseInt(author_id));
			if(book.isPresent()) {
				if(!book.get().isActive() && block.equalsIgnoreCase("yes")) {
					return new ResponseEntity<String>("Your trying to block the inactive book!", HttpStatus.OK);
				}
				
				if(book.get().isActive() && block.equalsIgnoreCase("no")) {
					return new ResponseEntity<String>("You trying to unblock the active book!", HttpStatus.OK);
				}
				if(block.equalsIgnoreCase("yes")) {
					book.get().setActive(false);				
					book_service.saveBook(book.get());
					return new ResponseEntity<String>("book blocked succesffuly..", HttpStatus.OK);
				}
				else {
					book.get().setActive(true);				
					book_service.saveBook(book.get());
					return new ResponseEntity<String>("book un-blocked succesffuly..", HttpStatus.OK);
				}
				
			}
			else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}


	@GetMapping("/getallbooks")
	public ResponseEntity<List<Book_Dto>> getAllBooks() {
		try {
			List<Book_Dto> books=book_service.getAllBooks();
			return new ResponseEntity<List<Book_Dto>>(books, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@GetMapping("/searchbookbytitle/{title}")
	public ResponseEntity<List<Book_Dto>> searchBookByTitle(@PathVariable(value="title") String title) {
		try {
			System.out.println(title);
			List<Book_Dto> book=book_service.searchBookByTitle(title.replace("-", "/"));
			System.out.println(book.toString());
			return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}

	@GetMapping("/searchbook")
	public ResponseEntity<List<Book_Dto>> searchBook(@RequestParam String category,@RequestParam String title,@RequestParam String author,@RequestParam String price,@RequestParam String publisher) {
		try {
			System.out.println("test");
			System.out.println(category);
			//List<Book_Dto> book=book_service.searchBookByFilters(category,title,author,Double.parseDouble(price),publisher);
			List<Book_Dto> book=book_service.searchBookByAllFilters(category,title,author,Double.parseDouble(price),publisher,"");
			
			
			return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/searchbooks")
	public ResponseEntity<List<Book_Dto>> searchBook(@RequestBody Book books) {
		try {
			String sql_quer="select b from Book b where ";
			boolean istrue=false;
			System.out.println("test");
			
			if(books.getCategory().length()>1) {
				sql_quer=sql_quer+"b.category=:category";
				istrue=true;
				System.out.println("test22");
			}
			
			if(books.getTitle().length()>1) {
				if(istrue) {
					sql_quer=sql_quer+" and ";
				}
				sql_quer=sql_quer+"b.title=:title";
				istrue=true;
			}
			
			if(books.getAuthor().length()>1) {
				if(istrue) {
					sql_quer=sql_quer+" and ";
				}
				sql_quer=sql_quer+"b.author=:author";
				istrue=true;
			}
			/*if(books.getPrice()!=Double.parseDouble("")) {
				System.out.println("testttt333");
				if(istrue) {
					sql_quer=sql_quer+" and ";
				}
				sql_quer=sql_quer+"b.price=?4";
				istrue=true;
			}*/
			if(istrue) {
				sql_quer=sql_quer+" and ";
			}
			sql_quer=sql_quer+"b.price=:price";
			istrue=true;
			if(books.getPublisher().length()>1) {
				if(istrue) {
					sql_quer=sql_quer+" and ";
				}
				sql_quer=sql_quer+"b.publisher=:publisher";
				istrue=true;
			}
			if(!istrue) {
				//return new ResponseEntity<List<Book_Dto>>(new Book_Dto,"please search with atleast one fiels"+HttpStatus.OK);
			}
			if(istrue) {
				sql_quer=sql_quer+" and ";
			}
			sql_quer=sql_quer+"b.active=:active";
			System.out.println(sql_quer);
			
			
			List<Book_Dto> book=book_service.searchBookByFilters(books.getCategory(),books.getTitle(),books.getAuthor(),books.getPrice(),books.getPublisher());
			
			return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/author/{authorid}")
	public ResponseEntity<List<Book>> searchBookByAuthorId(@PathVariable(value="authorid") String author_id) {
		try {
			List<Book> book=book_service.searchBookByAuthorId(Integer.parseInt(author_id));
			return new ResponseEntity<List<Book>>(book, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Book>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}
	
	@GetMapping("/author/{authorid}/block/{active}")
	public ResponseEntity<List<Book_Dto>> searchBookByAuthorIdAndActive(@PathVariable(value="authorid") String author_id,@PathVariable(value="active") String active) {
		try {
			
			if(active.equalsIgnoreCase("yes")) {
				System.out.println("yeesss");
				List<Book_Dto> book=book_service.searchBookByAuthorIdAndActive(Integer.parseInt(author_id),false);
				return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
			}
			else {
				System.out.println("nooo");
				List<Book_Dto> book=book_service.searchBookByAuthorIdAndActive(Integer.parseInt(author_id),true);
				return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}
	
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribeBook(@RequestBody Subscribe subs) {
		try {
			Optional<Book> book=book_service.getbookbyid(subs.getBook_id());
			if(book.isPresent()) {
				Optional<Subscribe> is_exists=reader_service.getsubscribeBook(subs.getEmail_id(),subs.getBook_id());
				if(is_exists.isPresent()) {
					return new ResponseEntity<String>("book is already subscribe..", HttpStatus.OK);
				}
				if(book.get().isActive()) {
					subs.setPrice(book.get().getPrice());
					DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
					Calendar obj = Calendar.getInstance();
					String str = formatter.format(obj.getTime());
					System.out.println("Current Date: "+str );
					subs.setIs_cancelled(false);
					subs.setDate(obj.getTimeInMillis()+"");
					int subs_id=reader_service.subscribeBook(subs);
					return new ResponseEntity<String>("book subscribed succesfully with subscription-id: "+subs_id, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>("book is blocked you cannot subscribe to it..", HttpStatus.OK);

				}
			}
			else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 

	}
	
	@GetMapping("/readers/{email_id}")
	public ResponseEntity<List<Book_Dto_new>> getAllSubscribeBooks(@PathVariable(value="email_id") String email_id) {
		try {
			List<Book_Dto_new> books=reader_service.getAllsubscribeBooks(email_id);
			return new ResponseEntity<List<Book_Dto_new>>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Book_Dto_new>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}")
	public ResponseEntity<List<Book_Dto>> getSubscribeBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id) {
		try {
			List<Book_Dto> books=reader_service.searchBookById(Integer.parseInt(subs_id),email_id);
			if(books!=null) {
				return new ResponseEntity<List<Book_Dto>>(books, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<Book_Dto>>(books, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}/reads")
	public ResponseEntity<List<Content>> getSubscribeBookContent(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id) {
		try {
			Optional<Book> books=reader_service.readBookById(Integer.parseInt(subs_id),email_id);
			if(books.isPresent()) {
				if(books.get().isActive()) {
					List<Content> b=new ArrayList<Content>();
					b.add(new Content(books.get().getContent()));
					return new ResponseEntity<List<Content>>(b, HttpStatus.OK);
				}else {
					List<Content> b=new ArrayList<Content>();
					return new ResponseEntity<List<Content>>(b, HttpStatus.NOT_FOUND);
				}
			}
			else {
				return new ResponseEntity<List<Content>>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<Content>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/subscription/{email_id}")
	public ResponseEntity<List<Subscribe>> getAllSubscribtionList(@PathVariable(value="email_id") String email_id) {
		try {
			List<Subscribe> subs=reader_service.getAllsubscribeList(email_id);
			return new ResponseEntity<List<Subscribe>>(subs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Subscribe>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/subscription/{email_id}/subsid/{subs_id}")
	public ResponseEntity<List<Invoice_Details>> getSubscribtionInvoice(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id) {
		try {
			System.out.println("testt");
			List<Invoice_Details> subs=reader_service.getSubscribtionInvoice(email_id,Integer.parseInt(subs_id));
			return new ResponseEntity<List<Invoice_Details>>(subs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Invoice_Details>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@PutMapping("/readers/{email_id}/subsid/{subs_id}/cancel_subscription")
	public ResponseEntity<String> unSubscribtionBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id) {
		try {
			System.out.println("testt");
			Optional<Subscribe> subs=reader_service.getSubscribtionDetails(email_id,Integer.parseInt(subs_id));
			if(subs.isPresent()) {
				System.out.println("present..");
				if(subs.get().isIs_cancelled()) {
					return new ResponseEntity<String>("Book is already Unsubscribe...",HttpStatus.OK);
				}
				// milliseconds
				String milliSec = subs.get().getDate();

				// Creating date format
				DateFormat simple = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

				// Creating date from milliseconds
				// using Date() constructor
				Date start_date = new Date(Long.parseLong(milliSec));

				// Formatting Date according to the
				// given format
				System.out.println(simple.format(start_date));
				
				// SimpleDateFormat converts the
				// string format to date object
				SimpleDateFormat sdf
					= new SimpleDateFormat(
						"dd-MM-yyyy HH:mm:ss");
				
				
				Calendar obj = Calendar.getInstance();
				
				Date end_date = new Date(Long.parseLong(obj.getTimeInMillis()+""));

				// Try Class
				try {

					// parse method is used to parse
					// the text from a string to
					// produce the date
					Date d1 = sdf.parse(simple.format(start_date));
					Date d2 = sdf.parse(simple.format(end_date));

					// Calucalte time difference
					// in milliseconds
					long difference_In_Time
						= d2.getTime() - d1.getTime();

					// Calucalte time difference in seconds,
					// minutes, hours, years, and days
					long difference_In_Seconds
						= TimeUnit.MILLISECONDS
							.toSeconds(difference_In_Time)
						% 60;

					long difference_In_Minutes
						= TimeUnit
							.MILLISECONDS
							.toMinutes(difference_In_Time)
						% 60;

					long difference_In_Hours
						= TimeUnit
							.MILLISECONDS
							.toHours(difference_In_Time)
						% 24;

					long difference_In_Days
						= TimeUnit
							.MILLISECONDS
							.toDays(difference_In_Time)
						% 365;

					long difference_In_Years
						= TimeUnit
							.MILLISECONDS
							.toDays(difference_In_Time)
						/ 365l;

					// Print the date difference in
					// years, in days, in hours, in
					// minutes, and in seconds
					System.out.print(
						"Difference"
						+ " between two dates is: ");

					// Print result
					System.out.println(
						difference_In_Years
						+ " years, "
						+ difference_In_Days
						+ " days, "
						+ difference_In_Hours
						+ " hours, "
						+ difference_In_Minutes
						+ " minutes, "
						+ difference_In_Seconds
						+ " seconds");
					if(difference_In_Years>=1 || difference_In_Days>=1) {
						
						return new ResponseEntity<String>("you can't unsubscribe a book...",HttpStatus.OK);
					}
					
						subs.get().setIs_cancelled(true);
						reader_service.subscribeBook(subs.get());
						
						
					
				}
				catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<String>("Unsubscribe successfully...",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@GetMapping("/searchbookbyfilter")
	public ResponseEntity<List<Book_Dto>> searchBookwithfilter(@RequestBody Book books) {
		try {

			List<Book_Dto> book=book_service.searchBookByAllFilters(books.getCategory(),books.getTitle(),books.getAuthor(),books.getPrice(),books.getPublisher(),books.getCreated_on());

			return new ResponseEntity<List<Book_Dto>>(book, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<List<Book_Dto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
