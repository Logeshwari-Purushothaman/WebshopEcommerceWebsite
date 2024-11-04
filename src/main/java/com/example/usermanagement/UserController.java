package com.example.usermanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// UserController handles requests related to user management
@RestController
@RequestMapping("/users") // Base URL for this controller
public class UserController {
    
    // List to hold user data
    private List<UserModel> users;

    // Constructor to initialize the user list with some default users
    public UserController() {
        this.users = new ArrayList<>();
        // Add some sample users to the list
        users.add(new UserModel(1L, "JohnDoe", "john@example.com", 30, "USER"));
        users.add(new UserModel(2L, "JaneSmith", "jane@example.com", 25, "ADMIN"));
    }

    // GET endpoint to retrieve the list of users
    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.ok(users); // Return the list of users with a 200 OK status
    }

    // POST endpoint to add a new user
    @PostMapping
    public ResponseEntity<UserModel> addUser(
            @RequestParam String username, 
            @RequestParam String email,
            @RequestParam int age, 
            @RequestParam String role) {
        
        // Create a new user with a unique ID and the provided details
        UserModel newUser = new UserModel((long) (users.size() + 1), username, email, age, role);
        users.add(newUser); // Add the new user to the list
        return ResponseEntity.ok(newUser); // Return the new user with a 200 OK status
    }
}
