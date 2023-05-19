package com.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.entity.Book;

@Repository
public interface Book_Repository extends JpaRepository<Book,Integer>{
	
	static String sql_query = "";

	//@Query("select b from Book b where b.title=?1 and b.active=?2")
	//List<Book> findBookByTitle(String title);
	@Query("select b from Book b where b.created_on=?1 and b.active=?2")
	List<Book> findBookByTitle(String created_on,boolean b);

	@Query("select b from Book b where b.book_id=?1 and b.author_id=?2")
	Optional<Book> findBookByAuthorandBookId(int book_id,int author_id);
	
	@Query("select b from Book b where b.category=?1 and b.title=?2 and b.author=?3 and b.price=?4 and b.publisher=?5 and b.active=?6")
	List<Book> findBookByFilters(String category,String title,String author,double price,String publisher,boolean block);
	
	//@Query("select b from Book b where (:category is null or b.category=:category) and (:title is not null or b.title=:title) and (:author is not null or b.author=:author) and (:price is not null or b.price=:price) and (:publisher is not null or b.publisher=:publisher) and b.active=:active")
	//List<Book> findBookByFilters(@Param("category") String category,@Param("title") String title,@Param("author") String author,@Param("price") double price,@Param("publisher") String publisher,@Param("active") boolean block);
	//@Query("SELECT t.name FROM User t where t.id = :id")
   // String findNameById(@Param("id") Long id);
	@Query("select b from Book b where b.author_id=?1")
	List<Book> findBookByAuthorId(int author_id);
	
	@Query("select b from Book b where b.author_id=?1 and b.active=?2")
	List<Book> findBookByAuthorIdAndActive(int author_id,boolean block);
	
	@Query("select b.book_id,b.title,b.category,b.author,b.publisher,b.published_date,b.price,b.logo from Book b")
	List<Object[]> findallbookds();
	
	@Query("select b from Book b where b.title=?1 and b.active=?2")
	List<Book> searchBookByTitle(String title,boolean block);
	
	@Query("select b from Book b where b.category=?1 and b.active=?2")
	List<Book> searchBookByCategory(String category,boolean block);
	
	@Query("select b from Book b where b.author=?1 and b.active=?2")
	List<Book> searchBookByAuthor(String author,boolean block);
	
	@Query("select b from Book b where b.publisher=?1 and b.active=?2")
	List<Book> searchBookByPublisher(String publisher,boolean block);
	
	@Query("select b from Book b where b.price=?1 and b.active=?2")
	List<Book> searchBookByPrice(double price,boolean block);

	@Query("select b from Book b where  b.created_on=?1 and b.active=?2")
	List<Book> searchBookBycreated_on(String created_on, boolean b);
}
