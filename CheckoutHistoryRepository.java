package com.bookStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.CheckoutHistory;
import com.bookStore.entity.User;

@Repository
public interface CheckoutHistoryRepository extends JpaRepository<CheckoutHistory, Long> {

	List<CheckoutHistory> findByUser(User user);
	
	
}
