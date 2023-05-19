package com.digitalbooks.book.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.book.client.BookMicroServiceClient;
import com.digitalbooks.book.dto.Book_Dto;
import com.digitalbooks.book.dto.Book_Dto_new;
import com.digitalbooks.book.dto.Content;
import com.digitalbooks.book.dto.Invoice_Details;
import com.digitalbooks.book.dto.LoginDto;
import com.digitalbooks.book.dto.SignUpDto;
import com.digitalbooks.book.dto.Subscribe;
import com.digitalbooks.book.dto.UserDto;
import com.digitalbooks.book.dto.Book;
import com.digitalbooks.book.entity.Role;
import com.digitalbooks.book.entity.User;
import com.digitalbooks.book.repository.RoleRepository;
import com.digitalbooks.book.repository.UserRepository;
import com.digitalbooks.book.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/digitalbooks")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private BookMicroServiceClient book_micro_service;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/check")
	public String check() {
		return "AuthController Working";
	}

	@PostMapping("/sign-up")
	public ResponseEntity<User> registerUser(@RequestBody SignUpDto signUpDto) {
		User u=new User();
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			
			u.setEmail("already taken");
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}
		System.out.println(signUpDto.getName()+" " +signUpDto.getEmail());
		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
	//	user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setPassword(signUpDto.getPassword());

		user.setRole(signUpDto.getRole());

		userRepository.save(user);
		u.setEmail("User registered successfully");
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@GetMapping("/sign-in")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
		/*Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);*/

		Optional<User> sig=userRepository.findByEmail(loginDto.getEmail());
		if(sig.isPresent()) {
			System.out.println("testtttt"+sig.get().getPassword());
			if(sig.get().getPassword().equalsIgnoreCase(loginDto.getPassword())) {
				System.out.println("success");
				return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Password is incorrect!!", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>("User not found!.", HttpStatus.NOT_FOUND);
		}
		
		
	
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<UserDto> authenticateUsernew(@RequestBody LoginDto loginDto) {
		System.out.println(loginDto.getEmail()+" "+loginDto.getPassword());
		UserDto u=new UserDto();
		Optional<User> sig=userRepository.findByEmail(loginDto.getEmail());
		if(sig.isPresent()) {
			System.out.println("testtttt");
			System.out.println(sig.get().getPassword());
			/*u.setMessagess("User signed-in successfully");
			u.setEmail(loginDto.getEmail());
			u.setId(sig.get().getId());
			u.setName(sig.get().getName());
			u.setRole(sig.get().getRole());
			return new ResponseEntity<UserDto>(u, HttpStatus.OK);*/
			if(sig.get().getPassword().equalsIgnoreCase(loginDto.getPassword())) {
				System.out.println("success");
				u.setMessagess("User signed-in successfully");
				u.setEmail(loginDto.getEmail());
				u.setId(sig.get().getId());
				u.setName(sig.get().getName());
				u.setRole(sig.get().getRole());
				System.out.println(sig.get().getName());
				return new ResponseEntity<UserDto>(u, HttpStatus.OK);
			}
			else {
				u.setMessagess("Password is incorrect");
				return new ResponseEntity<UserDto>(u, HttpStatus.NOT_FOUND);
			}
		}
		else {
			u.setMessagess("User not found");
			return new ResponseEntity<UserDto>(u, HttpStatus.NOT_FOUND);
		}
		
		
		
	}
	
	@PostMapping("/author/{authorid}/books")
	public ResponseEntity<String> createBook(@PathVariable(value="authorid") String authorid,@RequestBody Book books){
		return book_micro_service.createBook(authorid, books);
	}

	@PutMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> editBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestBody Book books){
		return book_micro_service.editBook(book_id, book_id, books);
	}
	
	@PostMapping("/author/{authorid}/books/{bookid}")
	public ResponseEntity<String> blockunblockBook(@PathVariable(value="authorid") String author_id,@PathVariable(value="bookid") String book_id,@RequestParam String block){
		return book_micro_service.blockunblockBook(author_id, book_id, block);
	}


	@GetMapping("/getallbooks")
	public ResponseEntity<List<Book_Dto>> getAllBooks(){
		System.out.println("test");
		return book_micro_service.getAllBooks();
	}

	@GetMapping("/searchbookbytitle/{title}")
	public ResponseEntity<List<Book_Dto>> searchBookByTitle(@PathVariable(value="title") String title){
		return book_micro_service.searchBookByTitle(title);
	}

	@GetMapping("/searchbook")
	public ResponseEntity<List<Book_Dto>> searchBook(@RequestParam String category,@RequestParam String title,@RequestParam String author,@RequestParam String price,@RequestParam String publisher){
		return book_micro_service.searchBook(category, title, author, price, publisher);
	}
	
	@GetMapping("/searchbookbyfilter")
	public ResponseEntity<List<Book_Dto>> searchBookwithfilter(@RequestBody Book books){
		return book_micro_service.searchBookwithfilter(books);
	}
	
	@GetMapping("/author/{authorid}")
	public ResponseEntity<List<Book>> searchBookByAuthorId(@PathVariable(value="authorid") String author_id){
		return book_micro_service.searchBookByAuthorId(author_id);
	}
	
	@GetMapping("/author/{authorid}/block/{active}")
	public ResponseEntity<List<Book_Dto>> searchBookByAuthorIdAndActive(@PathVariable(value="authorid") String author_id,@PathVariable(value="active") String active){
		return book_micro_service.searchBookByAuthorIdAndActive(author_id, active);
	}
	
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribeBook(@RequestBody Subscribe subs){
		return book_micro_service.subscribeBook(subs);
	}
	
	@GetMapping("/readers/{email_id}")
	public ResponseEntity<List<Book_Dto_new>> getAllSubscribeBooks(@PathVariable(value="email_id") String email_id){
		return book_micro_service.getAllSubscribeBooks(email_id);
	}
	
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}")
	public ResponseEntity<List<Book_Dto>> getSubscribeBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id){
		return book_micro_service.getSubscribeBook(email_id, subs_id);
	}
	
	@GetMapping("/readers/{email_id}/subscription/{subs_id}/reads")
	public ResponseEntity<List<Content>> getSubscribeBookContent(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id){
		return book_micro_service.getSubscribeBookContent(email_id, subs_id);
	}
	
	@GetMapping("/subscription/{email_id}")
	public ResponseEntity<List<Subscribe>> getAllSubscribtionList(@PathVariable(value="email_id") String email_id){
		return book_micro_service.getAllSubscribtionList(email_id);
	}
	
	@GetMapping("/subscription/{email_id}/subsid/{subs_id}")
	public ResponseEntity<List<Invoice_Details>> getSubscribtionInvoice(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id){
		return book_micro_service.getSubscribtionInvoice(email_id, subs_id);
	}
	
	@PutMapping("/readers/{email_id}/subsid/{subs_id}/cancel_subscription")
	public ResponseEntity<String> unSubscribtionBook(@PathVariable(value="email_id") String email_id,@PathVariable(value="subs_id") String subs_id) {
		return book_micro_service.unSubscribtionBook(email_id, subs_id);
	}


}
