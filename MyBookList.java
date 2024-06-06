package com.bookStore.entity;

import javax.persistence.*;

@Entity
@Table(name = "my_books")
public class MyBookList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String author;
    private String name;
    private String price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity; // Add quantity field
    private double totalPrice;
	public MyBookList(Integer id, String author, String name, String price, User user,int quantity,double totalPrice) {
		super();
		this.id = id;
		this.author = author;
		this.name = name;
		this.price = price;
		this.user = user;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public MyBookList() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

   
    
}
