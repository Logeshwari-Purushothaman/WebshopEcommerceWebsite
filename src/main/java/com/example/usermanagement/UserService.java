
package com.example.usermanagement;

import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class to manage user-related operations.
 * <p>
 * This service provides functionalities for managing users, including retrieving all users 
 * and getting a specific user ID. It holds a hardcoded list of users for demonstration purposes.
 * </p>
 */
@Service
public class UserService {

    /**
     * List of users managed by the service.
     */
    private List<UserModel> users;

    /**
     * Initializes the {@code UserService} with a hardcoded list of users.
     * <p>
     * This constructor populates the list with predefined user data for demonstration purposes.
     * </p>
     */
    public UserService() {
        this.users = List.of(
            new UserModel(1L, "johndoe", "johndoe@example.com", 28, "Admin"),
            new UserModel(2L, "janedoe", "janedoe@example.com", 34, "User"),
            new UserModel(3L, "mikeb", "mikeb@example.com", 22, "Moderator")
        );
    }
    
    /**
     * Retrieves a hardcoded user ID.
     * <p>
     * This method is used for demonstration purposes in the order processing functionality.
     * In a real-world scenario, this would typically retrieve the ID of the currently logged-in user.
     * </p>
     * 
     * @return a Long representing the user ID
     */
    public Long getCurrentUserId() {
        // This is a placeholder. In a real application, you would implement logic to retrieve the current user's ID
        // For example, you might get it from a security context or a session
        return 1L; // Returning a dummy user ID for now
    }


    /**
     * Retrieves the list of all users.
     * 
     * @return a list of {@code UserModel} objects representing all users
     */
    public List<UserModel> getAllUsers() {
        return users;
    }
    
}