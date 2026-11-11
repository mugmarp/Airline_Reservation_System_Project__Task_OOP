/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.passenger;
import java.io.Serializable;

/**
 * Request to add a new passenger.
 */
public class AddPassengerRequest extends Request {
    private static final long serialVersionUID = 1L;
    private passenger passenger;
    
    public AddPassengerRequest(passenger passenger) {
        this.passenger = passenger;
    }
    
    public passenger getPassenger() { return passenger; }
    public void setPassenger(passenger passenger) { this.passenger = passenger; }
    
    @Override public String getRequestType() { return "ADD_PASSENGER"; }
}