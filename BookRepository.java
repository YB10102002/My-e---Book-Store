package com.bookStore.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer>  {

	//List<Book> findByUser(User user);
	
	Optional<Book> findByNameAndAuthor(String name, String author);

}
