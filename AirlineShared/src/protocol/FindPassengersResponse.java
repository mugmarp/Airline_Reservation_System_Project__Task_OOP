/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.passenger;
import java.io.Serializable;
import java.util.List;

/**
 * Response for finding passengers.
 */
public class FindPassengersResponse extends Response {
    private static final long serialVersionUID = 1L;
    private List<passenger> passengers;
    
    public FindPassengersResponse(boolean success, List<passenger> passengers) {
        super(success);
        this.passengers = passengers;
    }
    
    public FindPassengersResponse(boolean success, String message, List<passenger> passengers) {
        super(success, message);
        this.passengers = passengers;
    }
    
    public List<passenger> getPassengers() { return passengers; }
    public void setPassengers(List<passenger> passengers) { this.passengers = passengers; }
}