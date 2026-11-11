/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

/**
 * Response for authentication request.
 */
public class AuthResponse extends Response {
    private static final long serialVersionUID = 1L;
    private String role; // "Admin" or "Agent"
    
    public AuthResponse(boolean success, String role) {
        super(success);
        this.role = role;
    }
    
    public AuthResponse(boolean success, String message, String role) {
        super(success, message);
        this.role = role;
    }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}