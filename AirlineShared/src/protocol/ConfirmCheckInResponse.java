/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Response for confirming check-in.
 */
public class ConfirmCheckInResponse extends Response {
    private static final long serialVersionUID = 1L;
    private String seat;
    private String boardingPassData;
    
    public ConfirmCheckInResponse(boolean success, String seat) {
        super(success);
        this.seat = seat;
    }
    
    public ConfirmCheckInResponse(boolean success, String message, String seat) {
        super(success, message);
        this.seat = seat;
    }
    
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    
    public String getBoardingPassData() { return boardingPassData; }
    public void setBoardingPassData(String boardingPassData) { this.boardingPassData = boardingPassData; }
}