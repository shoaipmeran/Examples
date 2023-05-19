package com.book.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dto.Book_Dto;
import com.book.entity.Book;
import com.book.entity.Subscribe;
import com.book.repository.Book_Repository;

@Service
public class Book_Service_Impl implements Book_Service{
	
	@Autowired
	Book_Repository book_repo;

	@Override
	public int saveBook(Book books) {
		Book book=book_repo.save(books);
		return book.getBook_id();
		
	}

	@Override
	public List<Book_Dto> getAllBooks() {
		//List<String> books=book_repo.findAll().stream().map(p->"book_id: " + p.getBook_id() + ", title: " + p.getTitle() + ", category: " + p.getCategory() + ", logo: " + p.getLogo()+ ", author: " +p.getAuthor() + ", publisher: " + p.getPublisher() + ", published_date: " + p.getPublished_date() + ", price: "+ p.getPrice())
			//	.collect(Collectors.toList());
		//return book_repo.findallbookds();
		/*List<Object[]> obj=(List<Object[]>)book_repo.findallbookds();
		List<Book_Dummy> books=new ArrayList<Book_Dummy>();
		for(Object[] o:obj) {
			try {
				System.out.println("testtt");
			books.add(new Book_Dummy(Integer.parseInt(o[0].toString()),o[1].toString(),o[2].toString(),o[3].toString(),o[4].toString(),o[5].toString(),Double.parseDouble(o[6].toString()),o[7].toString()));
		}
			catch(NullPointerException e) {
				System.out.println(e.toString());
			}
		}
	return books;*/
		List<Book> b= book_repo.findAll();
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:b) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}
		return new_list;
	}

	@Override
	public List<Book_Dto> searchBookByTitle(String title) {
		List<Book> b=  book_repo.findBookByTitle(title,true);
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:b) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}
		return new_list;
	}

	@Override
	public Optional<Book> searchBookByAuthorAndBookId(int book_id, int author_id) {
		Optional<Book> book=book_repo.findBookByAuthorandBookId(book_id,author_id);
		/*if(book.isPresent()) {
			if(book.get().getAuthor_id()==author_id) {
				return book;
			}
		}*/
		return book;
	}

	@Override
	public List<Book_Dto> searchBookByFilters(String category, String title, String author, double price,
			String publisher) {
		
		List<Book> book=book_repo.findBookByFilters(category,title,author,price,publisher,true);
		
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:book) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}
		return new_list;
	}

	@Override
	public List<Book> searchBookByAuthorId(int author_id) {
		List<Book> book=book_repo.findBookByAuthorId(author_id);
		/*List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:book) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}*/
		return book;
	}

	@Override
	public List<Book_Dto> searchBookByAuthorIdAndActive(int author_id, boolean block) {
		List<Book> book=book_repo.findBookByAuthorIdAndActive(author_id,block);
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:book) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
		}
		return new_list;
	}

	@Override
	public Optional<Book> getbookbyid(int bookid) {
		// TODO Auto-generated method stub
		return book_repo.findById(bookid);
	}

	@Override
	public List<Book_Dto> searchBookByAllFilters(String category, String title, String author, double price,
			String publisher,String created_on) {
		TreeSet<Book> books=  new TreeSet<>(Comparator.comparing(Book::getBook_id));
		boolean isaut=false;
		boolean ispub=false;
		boolean iscat=false;
		boolean istit=false;
		boolean isprice=false;
		boolean iscred=false;
		if(category!=null && category!="" && !category.isEmpty()) {
			System.out.println("test ::"+category+"..");
			List<Book> book=book_repo.searchBookByCategory(category,true);
			if(book!=null && book.size()>0) {
				books.addAll(book);
				iscat=true;
			}
		}

		if(author!=null && author!="" && !author.isEmpty()) {
			System.out.println("test2222222");
			List<Book> book=book_repo.searchBookByAuthor(author,true);
			if(book!=null && book.size()>0) {
				books.addAll(book);
				isaut=true;
			}
		}

		if(title!=null && title!="" && !title.isEmpty()) {
			List<Book> book=book_repo.searchBookByTitle(title,true);
			if(book!=null && book.size()>0) {
				books.addAll(book);
				istit=true;
			}
		}

		if(publisher!=null && publisher!="" && !publisher.isEmpty()) {
			List<Book> book=book_repo.searchBookByPublisher(publisher,true);
			if(book!=null && book.size()>0) {
				books.addAll(book);
				ispub=true;
			}
		}
		if(price>0) {
			//String pp=String.valueOf(price);
			//System.out.println("pppp"+pp);
			List<Book> book=book_repo.searchBookByPrice(price,true);
			System.out.println("price----");
			if(book!=null && book.size()>0) {
				books.addAll(book);
				isprice=true;
			}
		}
		if(created_on!=null && created_on!="" && !created_on.isEmpty()) {
			List<Book> book=book_repo.searchBookBycreated_on(created_on,true);
			if(book!=null && book.size()>0) {
				books.addAll(book);
				iscred=true;
			}
		}


		
		List<Book> booklist=new ArrayList<Book>(books);
		System.out.println("tesst--");
		if(ispub) {
			System.out.println("erorr111111r--");
			booklist=booklist.stream()
					.filter(p->p.getPublisher().equalsIgnoreCase(publisher))
					.collect(Collectors.toList());
			
		}
		if(iscred) {
			System.out.println("2222222222222--");
			booklist=booklist.stream()
					.filter(p->p.getCreated_on()!=null && p.getCreated_on().equalsIgnoreCase(created_on))
					.collect(Collectors.toList());
			System.out.println("2222222222222--");
		}
		if(isaut) {
			System.out.println("33333333333--");
			booklist=booklist.stream()
					.filter(p->p.getAuthor().equalsIgnoreCase(author))
					.collect(Collectors.toList());
			System.out.println("33333333333--");
		}
		if(istit) {
			System.out.println("44444444--");
			booklist=booklist.stream()
					.filter(p->p.getTitle().equalsIgnoreCase(title))
					.collect(Collectors.toList());
			System.out.println("44444444--");
		}
		if(iscat) {
			System.out.println("5555555--");
			booklist=booklist.stream()
					.filter(p->p.getCategory().equalsIgnoreCase(category))
					.collect(Collectors.toList());
			System.out.println("5555555--");
		}
		if(isprice) {
			System.out.println("555555555--");
			
			booklist=booklist.stream()
					.filter(p->p.getPrice()==price)
					.collect(Collectors.toList());
			System.out.println("66666666666--");
			
			
		}
		
		List<Book_Dto> new_list=new ArrayList<Book_Dto>();
		for(Book bokk:booklist) {
			new_list.add(new Book_Dto(bokk.getBook_id(),bokk.getTitle(),bokk.getCategory(),bokk.getAuthor(),bokk.getPublisher(),bokk.getPublished_date(),bokk.getPrice(),bokk.getLogo(),bokk.isActive()));
			System.out.println(bokk.toString());
		}
		return new_list;

	}

	

	
}
