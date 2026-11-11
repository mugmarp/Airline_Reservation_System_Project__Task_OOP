/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import protocol.*;
import model.passenger;
import model.flight;
import model.User;
import model.BookingInfo;
import Config.Config;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Client-side stub/proxy for communicating with the Airline Server.
 * Uses object serialization over TCP. All calls are async (non-blocking on EDT).
 */
public class ClientStub {
    
    private final String host;
    private final int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Consumer<Response> responseHandler;
    private boolean connected = false;
    
    public ClientStub() {
        this.host = Config.get("client.host");
        this.port = Config.getInt("client.port");
    }
    
    public ClientStub(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    /**
     * Connect to the server. Must be called before any requests.
     */
    public boolean connect() {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;
            
            // Start background listener for async responses
            executor.submit(this::listenForResponses);
            System.out.println("Connected to server at " + host + ":" + port);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Send a request and handle response asynchronously.
     */
    public void sendRequest(Request request, Consumer<Response> handler) {
        if (!connected) {
            handler.accept(new Response(false, "Not connected to server") {});
            return;
        }
        this.responseHandler = handler;
        executor.submit(() -> {
            try {
                out.writeObject(request);
                out.flush();
            } catch (IOException e) {
                System.err.println("Send failed: " + e.getMessage());
                handler.accept(new Response(false, "Send failed: " + e.getMessage()) {});
            }
        });
    }
    
    /**
     * Blocking send for simple cases (use sparingly, not on EDT).
     */
    public Response sendRequestBlocking(Request request, long timeoutMs) {
        if (!connected) {
            return new Response(false, "Not connected") {};
        }
        try {
            out.writeObject(request);
            out.flush();
            
            // Wait for response with timeout
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < timeoutMs) {
                if (in.available() > 0) {
                    return (Response) in.readObject();
                }
                Thread.sleep(50);
            }
            return new Response(false, "Timeout waiting for response") {};
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            return new Response(false, "Error: " + e.getMessage()) {};
        }
    }
    
    /**
     * Background listener for async responses.
     */
    private void listenForResponses() {
        try {
            while (connected && !socket.isClosed()) {
                Object obj = in.readObject();
                if (obj instanceof Response) {
                    Response response = (Response) obj;
                    if (responseHandler != null) {
                        responseHandler.accept(response);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            if (connected) {
                System.err.println("Connection lost: " + e.getMessage());
                connected = false;
            }
        }
    }
    
    // ===== Convenience methods for common operations =====
    
    public void authenticate(String username, String password, Consumer<AuthResponse> handler) {
        sendRequest(new AuthRequest(username, password), r -> handler.accept((AuthResponse) r));
    }
    
    public void addPassenger(passenger p, Consumer<AddPassengerResponse> handler) {
        sendRequest(new AddPassengerRequest(p), r -> handler.accept((AddPassengerResponse) r));
    }
    
    public void findPassengers(String passportQuery, Consumer<FindPassengersResponse> handler) {
        sendRequest(new FindPassengersRequest(passportQuery), r -> handler.accept((FindPassengersResponse) r));
    }
    
    public void deletePassenger(int passengerId, Consumer<DeletePassengerResponse> handler) {
        sendRequest(new DeletePassengerRequest(passengerId), r -> handler.accept((DeletePassengerResponse) r));
    }
    
    public void searchFlights(String origin, String destination, Consumer<SearchFlightsResponse> handler) {
        sendRequest(new SearchFlightsRequest(origin, destination), r -> handler.accept((SearchFlightsResponse) r));
    }
    
    public void getAllFlights(Consumer<SearchFlightsResponse> handler) {
        sendRequest(new GetAllFlightsRequest(), r -> handler.accept((SearchFlightsResponse) r));
    }
    
    public void updateFlightStatus(int scheduleId, String status, Consumer<UpdateFlightStatusResponse> handler) {
        sendRequest(new UpdateFlightStatusRequest(scheduleId, status), r -> handler.accept((UpdateFlightStatusResponse) r));
    }

    // ===== User Management Methods =====
    
    public void findUsers(String usernameQuery, Consumer<FindUsersResponse> handler) {
        sendRequest(new FindUsersRequest(usernameQuery), r -> handler.accept((FindUsersResponse) r));
    }
    
    public void addUser(User user, Consumer<AddUserResponse> handler) {
        sendRequest(new AddUserRequest(user), r -> handler.accept((AddUserResponse) r));
    }
    
    public void deleteUser(int userId, Consumer<DeleteUserResponse> handler) {
        sendRequest(new DeleteUserRequest(userId), r -> handler.accept((DeleteUserResponse) r));
    }
    
    public void updateUser(int userId, String password, String fullName, String role, Consumer<UpdateUserResponse> handler) {
        UpdateUserRequest request = new UpdateUserRequest(userId);
        request.setPassword(password);
        request.setFullName(fullName);
        request.setRole(role);
        sendRequest(request, r -> handler.accept((UpdateUserResponse) r));
    }

    // ===== Report Generation Methods =====
    
    public void generateReport(GenerateReportRequest.ReportType type, Consumer<GenerateReportResponse> handler) {
        sendRequest(new GenerateReportRequest(type), r -> handler.accept((GenerateReportResponse) r));
    }
    
    public void generateReport(GenerateReportRequest.ReportType type, int passengerId, int scheduleId, Consumer<GenerateReportResponse> handler) {
        sendRequest(new GenerateReportRequest(type, passengerId, scheduleId), r -> handler.accept((GenerateReportResponse) r));
    }

    // ===== Check-In Methods =====
    
    public void findBookingForCheckIn(String passportNumber, Consumer<FindBookingForCheckInResponse> handler) {
        sendRequest(new FindBookingForCheckInRequest(passportNumber), r -> handler.accept((FindBookingForCheckInResponse) r));
    }
    
    public void confirmCheckIn(int bookingId, String seat, Consumer<ConfirmCheckInResponse> handler) {
        sendRequest(new ConfirmCheckInRequest(bookingId, seat), r -> handler.accept((ConfirmCheckInResponse) r));
    }

    // ===== End Check-In Methods =====
    
    public void disconnect() {
        connected = false;
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        executor.shutdownNow();
    }
    
    public boolean isConnected() { return connected; }
}