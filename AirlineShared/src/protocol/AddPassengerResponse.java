/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

/**
 * Response for adding a passenger.
 */
public class AddPassengerResponse extends Response {
    private static final long serialVersionUID = 1L;
    private int passengerId; // generated ID from DB
    
    public AddPassengerResponse(boolean success, int passengerId) {
        super(success);
        this.passengerId = passengerId;
    }
    
    public AddPassengerResponse(boolean success, String message, int passengerId) {
        super(success, message);
        this.passengerId = passengerId;
    }
    
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
}