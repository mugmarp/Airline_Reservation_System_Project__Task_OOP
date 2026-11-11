/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package protocol;

import java.io.Serializable;

/**
 * Base class for all client-to-server requests.
 */
public abstract class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Request type identifier for server-side dispatching
    public abstract String getRequestType();
}