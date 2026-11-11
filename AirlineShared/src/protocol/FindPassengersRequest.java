/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Request to find/search passengers by passport number.
 */
public class FindPassengersRequest extends Request {
    private static final long serialVersionUID = 1L;
    private String passportQuery;
    
    public FindPassengersRequest(String passportQuery) {
        this.passportQuery = passportQuery;
    }
    
    public String getPassportQuery() { return passportQuery; }
    public void setPassportQuery(String passportQuery) { this.passportQuery = passportQuery; }
    
    @Override public String getRequestType() { return "FIND_PASSENGERS"; }
}