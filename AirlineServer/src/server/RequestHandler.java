/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import protocol.*;
import dao.passengerDAO;
import dao.flightDAO;
import dao.UserDAO;
import model.passenger;
import model.flight;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles incoming client requests on the server side.
 * Each client connection gets its own RequestHandler instance.
 */
public class RequestHandler implements Runnable {
    
    private final Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final passengerDAO passengerDAO = new passengerDAO();
    private final flightDAO flightDAO = new flightDAO();
    private final UserDAO userDAO = new UserDAO();
    private boolean running = true;
    
    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            
            System.out.println("Client connected: " + clientSocket.getInetAddress());
            
            while (running && !clientSocket.isClosed()) {
                Object obj = in.readObject();
                if (obj instanceof Request) {
                    Request request = (Request) obj;
                    Response response = processRequest(request);
                    sendResponse(response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (running) {
                System.err.println("Client handler error: " + e.getMessage());
            }
        } finally {
            close();
        }
    }
    
    private Response processRequest(Request request) {
        try {
            switch (request.getRequestType()) {
                case "AUTH":
                    return handleAuth((AuthRequest) request);
                case "ADD_PASSENGER":
                    return handleAddPassenger((AddPassengerRequest) request);
                case "FIND_PASSENGERS":
                    return handleFindPassengers((FindPassengersRequest) request);
                case "DELETE_PASSENGER":
                    return handleDeletePassenger((DeletePassengerRequest) request);
                case "SEARCH_FLIGHTS":
                    return handleSearchFlights((SearchFlightsRequest) request);
                case "GET_ALL_FLIGHTS":
                    return handleGetAllFlights((GetAllFlightsRequest) request);
                case "UPDATE_FLIGHT_STATUS":
                    return handleUpdateFlightStatus((UpdateFlightStatusRequest) request);
                case "FIND_USERS":
                    return handleFindUsers((FindUsersRequest) request);
                case "ADD_USER":
                    return handleAddUser((AddUserRequest) request);
                case "DELETE_USER":
                    return handleDeleteUser((DeleteUserRequest) request);
                case "UPDATE_USER":
                    return handleUpdateUser((UpdateUserRequest) request);
                default:
                    return new Response(false, "Unknown request type: " + request.getRequestType()) {};
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, "Server error: " + e.getMessage()) {};
        }
    }
    
    private AuthResponse handleAuth(AuthRequest request) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (var con = DB.DBConnection.getConnection();
             var pst = con.prepareStatement(sql)) {
            pst.setString(1, request.getUsername());
            pst.setString(2, request.getPassword());
            try (var rs = pst.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    return new AuthResponse(true, role);
                } else {
                    return new AuthResponse(false, "Invalid credentials", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResponse(false, "Database error: " + e.getMessage(), null);
        }
    }
    
    private AddPassengerResponse handleAddPassenger(AddPassengerRequest request) {
        int passengerId = passengerDAO.addPassenger(request.getPassenger());
        if (passengerId > 0) {
            return new AddPassengerResponse(true, passengerId);
        } else {
            return new AddPassengerResponse(false, "Failed to add passenger (passport may exist)", -1);
        }
    }
    
    private FindPassengersResponse handleFindPassengers(FindPassengersRequest request) {
        List<passenger> passengers = passengerDAO.findPassengersByPassport(request.getPassportQuery());
        return new FindPassengersResponse(true, passengers);
    }
    
    private DeletePassengerResponse handleDeletePassenger(DeletePassengerRequest request) {
        boolean deleted = passengerDAO.deletePassenger(request.getPassengerId());
        if (deleted) {
            return new DeletePassengerResponse(true);
        } else {
            return new DeletePassengerResponse(false, "Failed to delete (may have bookings)");
        }
    }
    
    private SearchFlightsResponse handleSearchFlights(SearchFlightsRequest request) {
        List<flight> flights = flightDAO.searchFlights(request.getOrigin(), request.getDestination());
        return new SearchFlightsResponse(true, flights);
    }
    
    private SearchFlightsResponse handleGetAllFlights(GetAllFlightsRequest request) {
        List<flight> flights = flightDAO.getAllFlights();
        return new SearchFlightsResponse(true, flights);
    }
    
    private UpdateFlightStatusResponse handleUpdateFlightStatus(UpdateFlightStatusRequest request) {
        boolean updated = flightDAO.updateFlightStatus(request.getScheduleId(), request.getStatus());
        if (updated) {
            return new UpdateFlightStatusResponse(true);
        } else {
            return new UpdateFlightStatusResponse(false, "Failed to update status");
        }
    }

    // ===== User Management Handlers =====
    
    private FindUsersResponse handleFindUsers(FindUsersRequest request) {
        List<User> users = userDAO.findUsers(request.getUsernameQuery());
        return new FindUsersResponse(true, users);
    }
    
    private AddUserResponse handleAddUser(AddUserRequest request) {
        int userId = userDAO.addUser(request.getUser());
        if (userId > 0) {
            return new AddUserResponse(true, userId);
        } else {
            return new AddUserResponse(false, "Failed to add user (username may exist)", -1);
        }
    }
    
    private DeleteUserResponse handleDeleteUser(DeleteUserRequest request) {
        boolean deleted = userDAO.deleteUser(request.getUserId());
        if (deleted) {
            return new DeleteUserResponse(true);
        } else {
            return new DeleteUserResponse(false, "Failed to delete user");
        }
    }
    
    private UpdateUserResponse handleUpdateUser(UpdateUserRequest request) {
        boolean updated = userDAO.updateUser(
            request.getUserId(),
            request.getPassword(),
            request.getFullName(),
            request.getRole()
        );
        if (updated) {
            return new UpdateUserResponse(true);
        } else {
            return new UpdateUserResponse(false, "Failed to update user");
        }
    }

    // ===== End User Management Handlers =====
    
    private void sendResponse(Response response) {
        try {
            out.writeObject(response);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void close() {
        running = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException ignored) {}
    }
}