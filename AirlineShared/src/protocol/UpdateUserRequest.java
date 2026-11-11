/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to update a user (password, role, full name).
 */
public class UpdateUserRequest extends Request {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String password; // new password (optional)
    private String fullName; // new full name (optional)
    private String role; // new role (optional)
    
    public UpdateUserRequest(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() { return userId; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setRole(String role) { this.role = role; }
    
    @Override public String getRequestType() { return "UPDATE_USER"; }
}