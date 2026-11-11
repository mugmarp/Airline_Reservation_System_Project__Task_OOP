/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import Config.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Airline Reservation System - Server Entry Point.
 * Listens for client connections and dispatches to RequestHandler threads.
 */
public class ServerMain {
    
    private static ServerSocket serverSocket;
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static volatile boolean running = true;
    
    public static void main(String[] args) {
        int port = Config.getInt("server.port");
        
        // Shutdown hook for graceful termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            running = false;
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException ignored) {}
            pool.shutdown();
            System.out.println("Server stopped.");
        }));
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Airline Server started on port " + port);
            System.out.println("Waiting for client connections...");
            
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    pool.submit(new RequestHandler(clientSocket));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Accept error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port " + port + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}