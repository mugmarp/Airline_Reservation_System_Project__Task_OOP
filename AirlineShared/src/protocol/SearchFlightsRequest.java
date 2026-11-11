/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to search flights by origin and destination.
 */
public class SearchFlightsRequest extends Request {
    private static final long serialVersionUID = 1L;
    private String origin;
    private String destination;
    
    public SearchFlightsRequest(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }
    
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    
    @Override public String getRequestType() { return "SEARCH_FLIGHTS"; }
}