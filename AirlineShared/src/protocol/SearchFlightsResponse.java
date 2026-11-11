/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import model.flight;
import java.io.Serializable;
import java.util.List;

/**
 * Response for searching flights.
 */
public class SearchFlightsResponse extends Response {
    private static final long serialVersionUID = 1L;
    private List<flight> flights;
    
    public SearchFlightsResponse(boolean success, List<flight> flights) {
        super(success);
        this.flights = flights;
    }
    
    public SearchFlightsResponse(boolean success, String message, List<flight> flights) {
        super(success, message);
        this.flights = flights;
    }
    
    public List<flight> getFlights() { return flights; }
    public void setFlights(List<flight> flights) { this.flights = flights; }
}