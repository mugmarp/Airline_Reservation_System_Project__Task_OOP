/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

/**
 * Response for updating flight status.
 */
public class UpdateFlightStatusResponse extends Response {
    private static final long serialVersionUID = 1L;
    
    public UpdateFlightStatusResponse(boolean success) {
        super(success);
    }
    
    public UpdateFlightStatusResponse(boolean success, String message) {
        super(success, message);
    }
}