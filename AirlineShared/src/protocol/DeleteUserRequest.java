/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to delete a user.
 */
public class DeleteUserRequest extends Request {
    private static final long serialVersionUID = 1L;
    private int userId;
    
    public DeleteUserRequest(int userId) {
        this.userId = userId;
    }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    @Override public String getRequestType() { return "DELETE_USER"; }
}