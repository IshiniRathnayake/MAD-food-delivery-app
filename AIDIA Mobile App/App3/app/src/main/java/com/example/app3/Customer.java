package com.example.app3;
public class Customer {
    private String username;
    private String email;
    private String phone;

    // Constructor
    public Customer(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
