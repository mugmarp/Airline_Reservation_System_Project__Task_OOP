/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import protocol.*;
import dao.passengerDAO;
import dao.flightDAO;
import dao.UserDAO;
import dao.CheckInDAO;
import model.passenger;
import model.flight;
import model.User;
import model.BookingInfo;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
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
    private final CheckInDAO checkInDAO = new CheckInDAO();
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
                case "GENERATE_REPORT":
                    return handleGenerateReport((GenerateReportRequest) request);
                case "FIND_BOOKING_CHECKIN":
                    return handleFindBookingCheckIn((FindBookingForCheckInRequest) request);
                case "CONFIRM_CHECKIN":
                    return handleConfirmCheckIn((ConfirmCheckInRequest) request);
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

    // ===== Report Generation Handler =====
    
    private GenerateReportResponse handleGenerateReport(GenerateReportRequest request) {
        try (Connection con = DB.DBConnection.getConnection()) {
            String sql = "";
            String reportTitle = "";
            String fileName = "";
            
            GenerateReportRequest.ReportType type = request.getReportType();
            
            switch (type) {
                case PASSENGERS:
                    sql = "SELECT passenger_id, first_name, last_name, nationality, passport_number FROM passengers";
                    reportTitle = "PASSENGER MANIFEST";
                    fileName = "All_Passengers_Report.pdf";
                    break;
                case FLIGHTS:
                    sql = "SELECT f.flight_number, a1.city as origin, a2.city as dest, fs.departure_time "
                            + "FROM flight_schedules fs JOIN flights f ON fs.flight_id = f.flight_id "
                            + "JOIN airports a1 ON f.origin_airport = a1.airport_code "
                            + "JOIN airports a2 ON f.dest_airport = a2.airport_code";
                    reportTitle = "FLIGHT SCHEDULES";
                    fileName = "All_Flights_Report.pdf";
                    break;
                case AIRPORTS:
                    sql = "SELECT airport_code, city, country FROM airports";
                    reportTitle = "AIRPORTS & ROUTES";
                    fileName = "Airports_Network.pdf";
                    break;
                case AIRCRAFT:
                    sql = "SELECT a.model, a.total_capacity, al.name as airline_name "
                            + "FROM aircrafts a JOIN airlines al ON a.airline_id = al.airline_id";
                    reportTitle = "AIRCRAFT FLEET DETAILS";
                    fileName = "Aircraft_Fleet.pdf";
                    break;
                case AIRLINES:
                    sql = "SELECT airline_id, name, iata_code FROM airlines";
                    reportTitle = "REGISTERED AIRLINES";
                    fileName = "Airlines_Directory.pdf";
                    break;
                case TICKETS:
                    // For ticket receipt, we need passengerId and scheduleId
                    sql = "SELECT p.first_name, p.last_name, p.passport_number, f.flight_number, "
                            + "a1.city as origin, a2.city as dest, fs.departure_time, fs.arrival_time "
                            + "FROM passengers p "
                            + "JOIN bookings b ON p.passenger_id = b.passenger_id "
                            + "JOIN flight_schedules fs ON b.schedule_id = fs.schedule_id "
                            + "JOIN flights f ON fs.flight_id = f.flight_id "
                            + "JOIN airports a1 ON f.origin_airport = a1.airport_code "
                            + "JOIN airports a2 ON f.dest_airport = a2.airport_code "
                            + "WHERE p.passenger_id = ? AND fs.schedule_id = ?";
                    
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setInt(1, request.getPassengerId());
                        pst.setInt(2, request.getScheduleId());
                        ResultSet rs = pst.executeQuery();
                        
                        List<String[]> headers = new ArrayList<>();
                        headers.add(new String[]{"Field", "Value"});
                        
                        List<String[]> rows = new ArrayList<>();
                        if (rs.next()) {
                            rows.add(new String[]{"Passenger", rs.getString("first_name") + " " + rs.getString("last_name")});
                            rows.add(new String[]{"Passport", rs.getString("passport_number")});
                            rows.add(new String[]{"Flight", rs.getString("flight_number")});
                            rows.add(new String[]{"From", rs.getString("origin")});
                            rows.add(new String[]{"To", rs.getString("dest")});
                            rows.add(new String[]{"Departure", rs.getString("departure_time")});
                            rows.add(new String[]{"Arrival", rs.getString("arrival_time")});
                        }
                        
                        return new GenerateReportResponse(true, "TICKET RECEIPT", headers, rows, "Ticket_Receipt_Passenger_" + request.getPassengerId() + ".pdf");
                    }
                default:
                    return new GenerateReportResponse(false, "Unknown report type: " + type);
            }
            
            // Execute the query for standard table reports
            try (PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                List<String[]> headers = new ArrayList<>();
                String[] headerRow = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    headerRow[i-1] = metaData.getColumnName(i);
                }
                headers.add(headerRow);
                
                List<String[]> rows = new ArrayList<>();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i-1] = rs.getString(i);
                    }
                    rows.add(row);
                }
                
                return new GenerateReportResponse(true, reportTitle, headers, rows, fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new GenerateReportResponse(false, "Server error: " + e.getMessage());
        }
    }
    
    // ===== End Report Generation Handler =====
    
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

    // ===== Check-In Handlers =====
    
    private FindBookingForCheckInResponse handleFindBookingCheckIn(FindBookingForCheckInRequest request) {
        BookingInfo booking = checkInDAO.findBookingByPassport(request.getPassportNumber());
        if (booking != null) {
            return new FindBookingForCheckInResponse(true, booking);
        } else {
            return new FindBookingForCheckInResponse(false, "No booking found for this passport number");
        }
    }
    
    private ConfirmCheckInResponse handleConfirmCheckIn(ConfirmCheckInRequest request) {
        // Generate seat if not provided
        String seat = request.getSeat();
        if (seat == null || seat.isEmpty()) {
            seat = checkInDAO.generateRandomSeat();
        }
        
        boolean confirmed = checkInDAO.confirmCheckIn(request.getBookingId(), seat);
        if (confirmed) {
            ConfirmCheckInResponse response = new ConfirmCheckInResponse(true, seat);
            // Add boarding pass data for PDF generation
            String boardingPassData = "Booking confirmed. Seat: " + seat;
            response.setBoardingPassData(boardingPassData);
            return response;
        } else {
            return new ConfirmCheckInResponse(false, "Failed to confirm check-in", null);
        }
    }

    // ===== End Check-In Handlers =====
}