package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {
        try {
            User user = userRepository.findByEmail(loginUser.getEmail());
            if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                return "Login Successful. User ID: " + user.getId();
            }
            return "Invalid email or password";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}