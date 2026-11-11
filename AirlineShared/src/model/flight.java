/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Flight DTO - shared between client and server.
 * Uses LocalDateTime for modern date-time handling.
 */
public class flight implements Serializable {

    private static final long serialVersionUID = 1L;

    private int scheduleId;
    private String flightNumber, origin, destination;
    private LocalDateTime departureTime, arrivalTime;

    public flight(int scheduleId, String flightNumber, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.scheduleId = scheduleId;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Getters
    public int getScheduleId() { return scheduleId; }
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }

    // Setters (for deserialization/DB mapping)
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
}