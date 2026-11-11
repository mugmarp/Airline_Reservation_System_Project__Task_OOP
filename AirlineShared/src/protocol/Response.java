/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Base class for all server-to-client responses.
 */
public abstract class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean success;
    private String message; // error message if success=false
    
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public Response(boolean success) {
        this(success, null);
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}