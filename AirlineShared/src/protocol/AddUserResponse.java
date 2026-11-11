/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Response for adding a user.
 */
public class AddUserResponse extends Response {
    private static final long serialVersionUID = 1L;
    private int userId;
    
    public AddUserResponse(boolean success, int userId) {
        super(success);
        this.userId = userId;
    }
    
    public AddUserResponse(boolean success, String message, int userId) {
        super(success, message);
        this.userId = userId;
    }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}