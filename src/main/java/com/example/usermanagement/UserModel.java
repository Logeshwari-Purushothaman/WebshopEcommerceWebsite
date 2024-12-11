package com.example.usermanagement;

/**
 * Represents a user entity with basic attributes.
 * <p>
 * This model class is used to store and manage user-related data such as ID, username, email, age, and role.
 * </p>
 */
public class UserModel {

    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Age of the user.
     */
    private int age;

    /**
     * Role assigned to the user (e.g., USER, ADMIN).
     */
    private String role;

    /**
     * Constructs a new {@code UserModel} with the specified details.
     * 
     * @param id       the unique identifier for the user
     * @param username the username of the user
     * @param email    the email address of the user
     * @param age      the age of the user
     * @param role     the role assigned to the user
     */
    public UserModel(Long id, String username, String email, int age, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    /**
     * Retrieves the unique identifier of the user.
     * 
     * @return the user's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the username of the user.
     * 
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the email address of the user.
     * 
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the age of the user.
     * 
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the role assigned to the user.
     * 
     * @return the user's role
     */
    public String getRole() {
        return role;
    }
}
