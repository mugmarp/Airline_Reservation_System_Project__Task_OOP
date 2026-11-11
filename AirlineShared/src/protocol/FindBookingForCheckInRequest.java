/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to find a booking by passport number for check-in.
 */
public class FindBookingForCheckInRequest extends Request {
    private static final long serialVersionUID = 1L;
    private String passportNumber;
    
    public FindBookingForCheckInRequest(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    
    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }
    
    @Override public String getRequestType() { return "FIND_BOOKING_CHECKIN"; }
}