package com.book.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.book.dto.Book_Dto_new;
import com.book.entity.Book;
import com.book.entity.Subscribe;

@Repository
public interface Reader_Repository extends JpaRepository<Subscribe,Integer>{

	@Query("select b from Book b JOIN Subscribe s ON b.book_id=s.book_id where s.email_id=?1 and s.is_cancelled=?2")
	List<Book> findBookByEmail(String email_id,boolean b);
	
	@Query("select b from Book b JOIN Subscribe s ON b.book_id=s.book_id where s.subscription_id=?1 and s.email_id=?2 and s.is_cancelled=?3")
	List<Book> findBookByEmailAndSubscribeId(int subscription_id,String email_id, boolean b);

	@Query("select b from Book b JOIN Subscribe s ON b.book_id=s.book_id where s.subscription_id=?1 and s.email_id=?2 and b.active=?3 and s.is_cancelled=?4")
	Optional<Book> readBookByEmailAndSubscribeId(int subs_id, String email_id,boolean active,boolean b);

	@Query("select s from Subscribe s where s.email_id=?1 and s.is_cancelled=?2")
	List<Subscribe> getSubscriptionListByEmail(String email_id,boolean b);

	@Query("select s from Subscribe s where s.email_id=?1 and s.subscription_id=?2 and s.is_cancelled=?3")
	List<Subscribe> getSubscriptionInvoiceByEmail(String email_id,int subs_id, boolean b);

	@Query("select s from Subscribe s where s.email_id=?1 and s.book_id=?2 and s.is_cancelled=?3")
	Optional<Subscribe> getSubscriptionBook(String email_id,int bookid, boolean b);

	@Query("select s from Subscribe s where s.email_id=?1 and s.subscription_id=?2 and s.is_cancelled=?3")
	Optional<Subscribe> getSubscribeBook(String email_id, int subs_id, boolean b);

	@Query("select s from Subscribe s where s.book_id=?1 and s.email_id=?2")
	List<Subscribe> findBookByEmails(int book_id,String email_id);
	
	
}
