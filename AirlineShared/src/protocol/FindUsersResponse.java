/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.User;
import java.io.Serializable;
import java.util.List;

/**
 * Response for finding users.
 */
public class FindUsersResponse extends Response {
    private static final long serialVersionUID = 1L;
    private List<User> users;
    
    public FindUsersResponse(boolean success, List<User> users) {
        super(success);
        this.users = users;
    }
    
    public FindUsersResponse(boolean success, String message, List<User> users) {
        super(success, message);
        this.users = users;
    }
    
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}