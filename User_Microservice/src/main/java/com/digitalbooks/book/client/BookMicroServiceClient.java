package com.digitalbooks.book.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.digitalbooks.book.dto.Book;
import com.digitalbooks.book.dto.Book_Dto;
import com.digitalbooks.book.dto.Book_Dto_new;
import com.digitalbooks.book.dto.Content;
import com.digitalbooks.book.dto.Invoice_Details;
import com.digitalbooks.book.dto.Subscribe;
import com.digitalbooks.book.dto.UserDto;

@FeignClient(value="BOOK-SERVICE", url="http://ec2-13-230-222-190.ap-northeast-1.compute.amazonaws.com:8080/api/v1/digitalbooks", decode404=true)
public interface BookMicroServiceClient {

	@PostMapping("/author/{authorid}/books")
	public ResponseEntity<String> createBook(@PathVariable(value="authorid") String authorid,@RequestBody Book books);

	@PutMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> editBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestBody Book books);
	
	@PostMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> blockunblockBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestParam String block);


	@GetMapping("/getallbooks")
	public ResponseEntity<List<Book_Dto>> getAllBooks();

	@GetMapping("/searchbookbytitle/{title}")
	public ResponseEntity<List<Book_Dto>> searchBookByTitle(@PathVariable(value="title") String title);

	@GetMapping("/searchbook")
	public ResponseEntity<List<Book_Dto>> searchBook(@RequestParam String category,@RequestParam String title,@RequestParam String author,@RequestParam String price,@RequestParam String publisher);
	
	@GetMapping("/searchbookbyfilter")
	public ResponseEntity<List<Book_Dto>> searchBookwithfilter(@RequestBody Book books);
	
	@GetMapping("/author/{authorid}")
	public ResponseEntity<List<Book>> searchBookByAuthorId(@PathVariable(value="authorid") String author_id);
	
	@GetMapping("/author/{authorid}/block/{active}")
	public ResponseEntity<List<Book_Dto>> searchBookByAuthorIdAndActive(@PathVariable(value="authorid") String author_id,@PathVariable(value="active") String active);
	
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribeBook(@RequestBody Subscribe subs);
	
	@GetMapping("/readers/{email_id}")
	public ResponseEntity<List<Book_Dto_new>> getAllSubscribeBooks(@PathVariable(value="email_id") String email_id);
	
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}")
	public ResponseEntity<List<Book_Dto>> getSubscribeBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id);
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}/reads")
	public ResponseEntity<List<Content>> getSubscribeBookContent(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id);
	
	@GetMapping("/subscription/{email_id}")
	public ResponseEntity<List<Subscribe>> getAllSubscribtionList(@PathVariable(value="email_id") String email_id);
	
	@GetMapping("/subscription/{email_id}/subsid/{subs_id}")
	public ResponseEntity<List<Invoice_Details>> getSubscribtionInvoice(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id);

	@PutMapping("/readers/{email_id}/subsid/{subs_id}/cancel_subscription")
	public ResponseEntity<String> unSubscribtionBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id);
}
