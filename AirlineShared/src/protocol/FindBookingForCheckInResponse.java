/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.BookingInfo;
import java.io.Serializable;

/**
 * Response for finding a booking for check-in.
 */
public class FindBookingForCheckInResponse extends Response {
    private static final long serialVersionUID = 1L;
    private BookingInfo booking;
    
    public FindBookingForCheckInResponse(boolean success, BookingInfo booking) {
        super(success);
        this.booking = booking;
    }
    
    public FindBookingForCheckInResponse(boolean success, String message) {
        super(success, message);
    }
    
    public BookingInfo getBooking() { return booking; }
    public void setBooking(BookingInfo booking) { this.booking = booking; }
}