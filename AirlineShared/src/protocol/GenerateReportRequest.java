/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;
import java.util.List;

/**
 * Request to generate a report. The server returns the data as rows of string arrays.
 * Report types: PASSENGERS, FLIGHTS, AIRPORTS, AIRCRAFT, AIRLINES, TICKETS
 */
public class GenerateReportRequest extends Request {
    private static final long serialVersionUID = 1L;
    
    public enum ReportType {
        PASSENGERS,      // All passengers manifest
        FLIGHTS,         // All flight schedules
        AIRPORTS,        // Airports network
        AIRCRAFT,        // Aircraft fleet details
        AIRLINES,        // Airlines directory
        TICKETS          // Ticket receipts (requires passengerId and scheduleId)
    }
    
    private ReportType reportType;
    private int passengerId;  // for TICKETS report
    private int scheduleId;   // for TICKETS report
    
    public GenerateReportRequest(ReportType reportType) {
        this.reportType = reportType;
    }
    
    public GenerateReportRequest(ReportType reportType, int passengerId, int scheduleId) {
        this.reportType = reportType;
        this.passengerId = passengerId;
        this.scheduleId = scheduleId;
    }
    
    public ReportType getReportType() { return reportType; }
    public int getPassengerId() { return passengerId; }
    public int getScheduleId() { return scheduleId; }
    
    @Override public String getRequestType() { return "GENERATE_REPORT"; }
}