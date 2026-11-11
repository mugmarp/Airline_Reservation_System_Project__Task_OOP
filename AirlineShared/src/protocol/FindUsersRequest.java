/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.User;
import java.io.Serializable;
import java.util.List;

/**
 * Request to find/search users.
 */
public class FindUsersRequest extends Request {
    private static final long serialVersionUID = 1L;
    private String usernameQuery; // search by username or full name
    
    public FindUsersRequest(String usernameQuery) {
        this.usernameQuery = usernameQuery;
    }
    
    public String getUsernameQuery() { return usernameQuery; }
    public void setUsernameQuery(String usernameQuery) { this.usernameQuery = usernameQuery; }
    
    @Override public String getRequestType() { return "FIND_USERS"; }
}