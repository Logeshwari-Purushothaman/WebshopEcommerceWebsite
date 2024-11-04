package com.example.usermanagement;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private List<UserModel> users;

    public UserService() {
        // Hardcoded list of users
        this.users = List.of(
            new UserModel(1L, "johndoe", "johndoe@example.com", 28, "Admin"),
            new UserModel(2L, "janedoe", "janedoe@example.com", 34, "User"),
            new UserModel(3L, "mikeb", "mikeb@example.com", 22, "Moderator")
        );
    }

    // Method to get all users
    public List<UserModel> getAllUsers() {
        return users;
    }
}
