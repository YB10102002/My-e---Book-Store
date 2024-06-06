package com.bookStore.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checkout_history")
public class CheckoutHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime checkoutDate;

    // Default constructor
    public CheckoutHistory() {
    }

    // Constructor with parameters
    public CheckoutHistory(User user, Book book, LocalDateTime checkoutDate) {
        this.user = user;
        this.book = book;
        this.checkoutDate = checkoutDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
