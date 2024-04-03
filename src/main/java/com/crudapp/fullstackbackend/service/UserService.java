package com.crudapp.fullstackbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.fullstackbackend.model.LoginRequest;
import com.crudapp.fullstackbackend.model.User;
import com.crudapp.fullstackbackend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User loginUser(LoginRequest loginRequest) {
        User u = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!u.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return u;
    }

    public User registerUser(User user) {
        // Check if user with the provided email already exists
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }

        // Save the new user if not already registered
        return userRepo.save(user);
    }

}
