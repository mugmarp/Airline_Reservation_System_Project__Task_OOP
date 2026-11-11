/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DB.DBConnection;
import model.BookingInfo;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Check-in Data Access Object for server-side database operations.
 */
public class CheckInDAO {

    /**
     * Find booking by passport number for check-in.
     * Returns booking info with passenger and flight details.
     */
    public BookingInfo findBookingByPassport(String passportNumber) {
        String sql = "SELECT b.booking_id, b.passenger_id, b.schedule_id, b.status as booking_status, b.seat, " +
                "p.first_name, p.last_name, p.passport_number, " +
                "f.flight_number, a1.city as origin, a2.city as destination, " +
                "fs.departure_time, fs.arrival_time " +
                "FROM bookings b " +
                "JOIN passengers p ON b.passenger_id = p.passenger_id " +
                "JOIN flight_schedules fs ON b.schedule_id = fs.schedule_id " +
                "JOIN flights f ON fs.flight_id = f.flight_id " +
                "JOIN airports a1 ON f.origin_airport = a1.airport_code " +
                "JOIN airports a2 ON f.dest_airport = a2.airport_code " +
                "WHERE p.passport_number = ? AND b.status IN ('Confirmed', 'Pending')";
        
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, passportNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    BookingInfo info = new BookingInfo();
                    info.setBookingId(rs.getInt("booking_id"));
                    info.setPassengerId(rs.getInt("passenger_id"));
                    info.setScheduleId(rs.getInt("schedule_id"));
                    info.setStatus(rs.getString("booking_status"));
                    info.setSeat(rs.getString("seat"));
                    info.setPassengerName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    info.setPassportNumber(rs.getString("passport_number"));
                    info.setFlightNumber(rs.getString("flight_number"));
                    info.setOrigin(rs.getString("origin"));
                    info.setDestination(rs.getString("destination"));
                    info.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                    info.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
                    info.setCheckedIn(rs.getString("seat") != null && !rs.getString("seat").isEmpty());
                    info.setSeat(rs.getString("seat"));
                    return info;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Confirm check-in for a booking with seat assignment.
     */
    public boolean confirmCheckIn(int bookingId, String seat) {
        String sql = "UPDATE bookings SET seat = ?, status = 'Checked-In' WHERE booking_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, seat);
            pst.setInt(2, bookingId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generate a random available seat (simple implementation).
     * In a real system, this would check seat availability.
     */
    public String generateRandomSeat() {
        // Simple seat generation: row 1-30, seat A-F
        Random rand = new Random();
        int row = rand.nextInt(30) + 1;
        char seatLetter = (char) ('A' + rand.nextInt(6)); // A-F
        return row + "" + seatLetter;
    }
}