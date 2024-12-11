package com.example.usermanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing users.
 * <p>
 * This controller provides endpoints to retrieve and add user data. 
 * It uses a basic in-memory list to simulate user management functionality.
 * </p>
 */
@RestController
@RequestMapping("/users") // Base URL for this controller
public class UserController {

    /**
     * List to hold user data in memory.
     */
    private List<UserModel> users;

    /**
     * Initializes the controller with some default users.
     * <p>
     * This constructor populates an in-memory list of users for demonstration purposes.
     * </p>
     */
    public UserController() {
        this.users = new ArrayList<>();
        // Add some sample users to the list
        users.add(new UserModel(1L, "JohnDoe", "john@example.com", 30, "USER"));
        users.add(new UserModel(2L, "JaneSmith", "jane@example.com", 25, "ADMIN"));
    }

    /**
     * Retrieves the list of users.
     * <p>
     * Handles HTTP GET requests to the base URL {@code /users}.
     * </p>
     *
     * @return a {@link ResponseEntity} containing the list of users with a 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.ok(users); // Return the list of users with a 200 OK status
    }

    /**
     * Adds a new user to the list.
     * <p>
     * Handles HTTP POST requests to the base URL {@code /users}.
     * The new user's details are provided as request parameters.
     * </p>
     *
     * @param username the username of the new user
     * @param email    the email address of the new user
     * @param age      the age of the new user
     * @param role     the role of the new user (e.g., USER, ADMIN)
     * @return a {@link ResponseEntity} containing the newly created user with a 200 OK status
     */
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
