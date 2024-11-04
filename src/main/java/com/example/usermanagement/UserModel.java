package com.example.usermanagement;

public class UserModel {
    private Long id;
    private String username;
    private String email;
    private int age;
    private String role;

    // Constructor
    public UserModel(Long id, String username, String email, int age, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }
}
