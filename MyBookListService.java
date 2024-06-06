package com.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.MyBookList;
import com.bookStore.entity.User;
import com.bookStore.entity.Book;
import com.bookStore.entity.CheckoutHistory;
import com.bookStore.repository.MyBookRepository;
import com.bookStore.repository.CheckoutHistoryRepository;

@Service
public class MyBookListService {

    @Autowired
    private MyBookRepository repository;

    @Autowired
    private CheckoutHistoryRepository checkoutHistoryRepository;

    public List<MyBookList> getAllMyBooks() {
        return repository.findAll();
    }

    public void clearAllMyBooks() {
        repository.deleteAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<MyBookList> getMyBooksByUser(User user) {
        return repository.findByUser(user);
    }

//    public void saveMyBooks(MyBookList book) {
//        repository.save(book);
//    }
//    public void saveMyBooks(MyBookList book) {
//        // Ensure that the associated Book entity is set
//        if (book.getBook() != null && book.getBook().getId() != null) {
//            // If the associated Book entity has an ID, fetch it from the repository
//            Book associatedBook = book.getBook();
//            book.setBook(repository.getOne(associatedBook.getId()));
//        }
//        repository.save(book);
//    }
    
    public MyBookList findByUserAndBook(User user, Book book) {
        return repository.findByUserAndBook(user, book);
    }

    public void saveMyBook(MyBookList myBookList) {
    	repository.save(myBookList);
    }

    public void saveCheckoutHistory(CheckoutHistory history) {
        checkoutHistoryRepository.save(history);
    }
}
