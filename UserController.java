package com.bookStore.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bookStore.dto.UserDto;
import com.bookStore.entity.MyBookList;
import com.bookStore.entity.User;
import com.bookStore.entity.Book;
import com.bookStore.entity.CheckoutHistory;
import com.bookStore.repository.CheckoutHistoryRepository;
import com.bookStore.service.MyBookListService;
import com.bookStore.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    private MyBookListService myBookListService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CheckoutHistoryRepository checkoutHistoryRepository;

    @GetMapping("/register")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute UserDto user, RedirectAttributes redirectAttributes) {

        try {
            userService.save(user);
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful!");
            return "redirect:/register?success=true";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("registrationError", "An error occurred while processing your registration.");
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, Principal p) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(p.getName());
        model.addAttribute("user", userDetails);
        return "home";
    }

    @GetMapping("/home1")
    public String home1(Model model, Principal p) {
        if (p == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(p.getName());
        model.addAttribute("user", userDetails);
        return "home1";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        List<MyBookList> userBooks = myBookListService.getMyBooksByUser(user);
        double total = userBooks.stream()
                .mapToDouble(book -> Double.parseDouble(book.getPrice()))
                .sum();

        model.addAttribute("books", userBooks);
        model.addAttribute("total", total);
        return "checkout";
    }

    @GetMapping("/process_checkout")
    public String processCheckout(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        List<MyBookList> userBooks = myBookListService.getMyBooksByUser(user);
        
        for (MyBookList myBookList : userBooks) {
            Book book = myBookList.getBook();
            if (book != null) {
                myBookList.setUser(user);
                myBookList.setBook(book);
                myBookListService.saveMyBook(myBookList);

                CheckoutHistory history = new CheckoutHistory(user, book, LocalDateTime.now());
                checkoutHistoryRepository.save(history);
                myBookListService.clearAllMyBooks();
            } else {
                System.out.print("Book have no Book Id...");
            }
        }

        return "redirect:/home1";
    }

    @GetMapping("/checkout1")
    public String checkoutDetails(Model model) {
        List<CheckoutHistory> checkoutDetails = checkoutHistoryRepository.findAll();
        model.addAttribute("checkoutDetails", checkoutDetails);
        return "checkout1";
    }

    @GetMapping("/userCheckoutHistory")
    public String userCheckoutHistory(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        List<CheckoutHistory> checkoutHistory = checkoutHistoryRepository.findByUser(user);
        model.addAttribute("checkoutHistory", checkoutHistory);
        return "userCheckoutHistory";
    }
}
