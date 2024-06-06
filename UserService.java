package com.bookStore.service;

import org.springframework.stereotype.Service;

import com.bookStore.dto.UserDto;
import com.bookStore.entity.User;

@Service
public interface UserService {
	
	
	User save(UserDto userDto);

	User findByEmail(String email);
}
