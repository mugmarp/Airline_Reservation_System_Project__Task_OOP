/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to get all flights (for admin management).
 */
public class GetAllFlightsRequest extends Request {
    private static final long serialVersionUID = 1L;
    
    public GetAllFlightsRequest() {}
    
    @Override public String getRequestType() { return "GET_ALL_FLIGHTS"; }
}