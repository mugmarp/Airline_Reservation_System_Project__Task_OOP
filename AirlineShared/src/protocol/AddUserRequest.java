/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.User;
import java.io.Serializable;

/**
 * Request to add a new user.
 */
public class AddUserRequest extends Request {
    private static final long serialVersionUID = 1L;
    private User user;
    
    public AddUserRequest(User user) {
        this.user = user;
    }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    @Override public String getRequestType() { return "ADD_USER"; }
}