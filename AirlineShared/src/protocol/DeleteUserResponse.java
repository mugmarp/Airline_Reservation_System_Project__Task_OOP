/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Response for deleting a user.
 */
public class DeleteUserResponse extends Response {
    private static final long serialVersionUID = 1L;
    
    public DeleteUserResponse(boolean success) {
        super(success);
    }
    
    public DeleteUserResponse(boolean success, String message) {
        super(success, message);
    }
}