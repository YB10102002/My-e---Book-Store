package com.bookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookStore.dto.UserDto;
import com.bookStore.entity.User;
import com.bookStore.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userR;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        String role = (userDto.getRole() != null) ? userDto.getRole() : "user";
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), role, userDto.getFullname());
        return userR.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userR.findByEmail(email);
    }
}
