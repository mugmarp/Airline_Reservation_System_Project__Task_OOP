/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.flight;
import java.io.Serializable;

/**
 * Request to update flight status.
 */
public class UpdateFlightStatusRequest extends Request {
    private static final long serialVersionUID = 1L;
    private int scheduleId;
    private String status; // "Scheduled", "Delayed", "Cancelled", "Completed"
    
    public UpdateFlightStatusRequest(int scheduleId, String status) {
        this.scheduleId = scheduleId;
        this.status = status;
    }
    
    public int getScheduleId() { return scheduleId; }
    public String getStatus() { return status; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public void setStatus(String status) { this.status = status; }
    
    @Override public String getRequestType() { return "UPDATE_FLIGHT_STATUS"; }
}