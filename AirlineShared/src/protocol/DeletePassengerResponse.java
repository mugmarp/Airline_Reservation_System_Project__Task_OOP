/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

/**
 * Response for deleting a passenger.
 */
public class DeletePassengerResponse extends Response {
    private static final long serialVersionUID = 1L;
    
    public DeletePassengerResponse(boolean success) {
        super(success);
    }
    
    public DeletePassengerResponse(boolean success, String message) {
        super(success, message);
    }
}