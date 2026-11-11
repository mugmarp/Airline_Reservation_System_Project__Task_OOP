/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.passenger;
import java.io.Serializable;
import java.util.List;

/**
 * Request to authenticate a user.
 */
public class AuthRequest extends Request {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    
    @Override public String getRequestType() { return "AUTH"; }
}