/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 * User DTO - shared between client and server.
 * Represents a system user (Admin or Agent).
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String fullName;
    private String password; // plain text for now (server should hash)
    private String role; // "Admin" or "Agent"

    public User() {}

    public User(String username, String fullName, String password, String role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String username, String fullName, String password, String role) {
        this(username, fullName, password, role);
        this.userId = userId;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setters (for deserialization/DB mapping)
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}