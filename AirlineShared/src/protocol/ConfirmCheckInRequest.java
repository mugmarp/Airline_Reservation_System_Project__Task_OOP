/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to confirm check-in for a booking.
 */
public class ConfirmCheckInRequest extends Request {
    private static final long serialVersionUID = 1L;
    private int bookingId;
    private String seat;
    
    public ConfirmCheckInRequest(int bookingId, String seat) {
        this.bookingId = bookingId;
        this.seat = seat;
    }
    
    public int getBookingId() { return bookingId; }
    public String getSeat() { return seat; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setSeat(String seat) { this.seat = seat; }
    
    @Override public String getRequestType() { return "CONFIRM_CHECKIN"; }
}