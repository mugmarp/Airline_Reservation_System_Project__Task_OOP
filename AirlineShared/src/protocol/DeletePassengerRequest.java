/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to delete a passenger.
 */
public class DeletePassengerRequest extends Request {
    private static final long serialVersionUID = 1L;
    private int passengerId;
    
    public DeletePassengerRequest(int passengerId) {
        this.passengerId = passengerId;
    }
    
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }
    
    @Override public String getRequestType() { return "DELETE_PASSENGER"; }
}