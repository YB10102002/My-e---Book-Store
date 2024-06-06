package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.entity.User;
import com.bookStore.repository.UserRepository;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private MyBookListService myBookService;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private User userService;
    @GetMapping("/book_register")
    public String bookRegister() {
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook() {
        List<Book> list = service.getAllBook();
        return new ModelAndView("bookList", "book", list);
    }

    @GetMapping("/available_books1")
    public ModelAndView getAllBook1() {
        List<Book> list = service.getAllBook();
        return new ModelAndView("bookList1", "book", list);
    }

    @PostMapping("/save")
    public String addOrUpdateBook(@ModelAttribute Book b) {
        service.save(b); // Save or update book in the database
        return "redirect:/available_books";
    }
    
//    @PostMapping("/save")
//    public String addBook(@ModelAttribute Book newBook) {
//        Optional<Book> existingBookOptional = service.findByNameAndAuthor(newBook.getName(), newBook.getAuthor());
//        if (existingBookOptional.isPresent()) {
//            // Book already exists, update quantity
//            Book existingBook = existingBookOptional.get();
//            int newQuantity = existingBook.getQuantity() + newBook.getQuantity();
//            existingBook.setQuantity(newQuantity);
//            service.save(existingBook);
//        } else {
//            // Book does not exist, save it
//            service.save(newBook);
//        }
//        return "redirect:/available_books";
//    }

    @GetMapping("/my_books")
    public String myBooks(Model model, Principal principal) {
        String email = principal.getName(); // Get current user's email
        User user = userRepository.findByEmail(email); // Find user by email
        List<MyBookList> userBooks = myBookService.getMyBooksByUser(user); // Find books associated with the user
        model.addAttribute("books", userBooks); // Add books to the model
        return "myBooks";
    }
    
//    @PostMapping("/my_Books")
//    public String getMyList(@RequestParam("userId") Long userId, @RequestParam("author") String author, @RequestParam("name") String name, @RequestParam("price") String price) {
//        User user = userService.findById(userId); // Assume you have a method to find a user by ID
//        MyBookList myBook = new MyBookList();
//        myBook.setAuthor(author);
//        myBook.setName(name);
//        myBook.setPrice(price);
//        myBook.setUser(user);
//        myBookService.saveMyBooks(myBook);
//        return "redirect:/my_Books";
//    }

//    @RequestMapping("/mylist/{id}")
//    public String getMyList(@PathVariable("id") int id, Principal principal) {
//        Book b = service.getBookById(id);
//        String email = principal.getName();
//        User user = userRepository.findByEmail(email);
//        MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice(), user);
//        myBookService.saveMyBooks(mb);
//        return "redirect:/my_books";
//    }
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id, Principal principal) {
        Book book = service.getBookById(id); // Retrieve the book by its ID
        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        // Create a new MyBookList instance and set its properties
        MyBookList myBookList = new MyBookList();
        myBookList.setAuthor(book.getAuthor());
        myBookList.setName(book.getName());
        myBookList.setPrice(book.getPrice());
        myBookList.setUser(user);
        myBookList.setBook(book); // Set the associated Book entity

        // Save the MyBookList entity
        myBookService.saveMyBook(myBookList);

        return "redirect:/my_books";
    }
//    @RequestMapping("/mylist/{id}")
//    public String addOrUpdateMyBookList(@PathVariable("id") int bookId, Principal principal) {
//        Book book = service.getBookById(bookId); // Retrieve the book by its ID
//        String email = principal.getName();
//        User user = userRepository.findByEmail(email);
//
//        MyBookList existingMyBook = myBookService.findByUserAndBook(user, book);
//        if (existingMyBook != null) {
//            // If the book already exists in the user's list, update quantity and totalPrice
//            existingMyBook.setQuantity(existingMyBook.getQuantity() + 1);
//            existingMyBook.setTotalPrice(existingMyBook.getTotalPrice() + Double.parseDouble(book.getPrice()));
//            myBookService.saveMyBook(existingMyBook);
//        } else {
//            // If the book doesn't exist, add it to the user's list
//            MyBookList myBookList = new MyBookList();
//            myBookList.setBook(book);
//            myBookList.setUser(user);
//            myBookList.setQuantity(1); // Initial quantity is 1
//            myBookList.setTotalPrice(Double.parseDouble(book.getPrice())); // Initial totalPrice is book price
//            myBookService.saveMyBook(myBookList);
//        }
//        return "redirect:/my_books";
//    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book b = service.getBookById(id);
        model.addAttribute("book", b);
        return "bookEdit";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/available_books";
    }

    @RequestMapping("/deleteBook1/{id}")
    public String deleteBook1(@PathVariable("id") int id) {
        myBookService.deleteById(id);
        return "redirect:/available_books1";
    }
}