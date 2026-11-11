/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Response for updating a user.
 */
public class UpdateUserResponse extends Response {
    private static final long serialVersionUID = 1L;
    
    public UpdateUserResponse(boolean success) {
        super(success);
    }
    
    public UpdateUserResponse(boolean success, String message) {
        super(success, message);
    }
}