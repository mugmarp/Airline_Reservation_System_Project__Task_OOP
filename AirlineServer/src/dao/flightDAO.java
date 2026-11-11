/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DB.DBConnection;
import model.flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Flight Data Access Object for server-side database operations.
 */
public class flightDAO {

    public List<flight> searchFlights(String origin, String destination) {
        List<flight> list = new ArrayList<>();
        String sql = "SELECT fs.schedule_id, f.flight_number, a1.city as origin, a2.city as dest, fs.departure_time, fs.arrival_time "
                + "FROM flight_schedules fs "
                + "JOIN flights f ON fs.flight_id = f.flight_id "
                + "JOIN airports a1 ON f.origin_airport = a1.airport_code "
                + "JOIN airports a2 ON f.dest_airport = a2.airport_code "
                + "WHERE a1.city = ? AND a2.city = ? AND fs.status = 'Scheduled'";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, origin);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new flight(
                        rs.getInt("schedule_id"), rs.getString("flight_number"),
                        rs.getString("origin"), rs.getString("dest"),
                        rs.getTimestamp("departure_time").toLocalDateTime(), rs.getTimestamp("arrival_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<flight> getAllFlights() {
        List<flight> list = new ArrayList<>();
        String sql = "SELECT fs.schedule_id, f.flight_number, a1.city as origin, a2.city as dest, fs.departure_time, fs.arrival_time, fs.status "
                + "FROM flight_schedules fs "
                + "JOIN flights f ON fs.flight_id = f.flight_id "
                + "JOIN airports a1 ON f.origin_airport = a1.airport_code "
                + "JOIN airports a2 ON f.dest_airport = a2.airport_code "
                + "ORDER BY fs.departure_time ASC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new flight(
                        rs.getInt("schedule_id"), rs.getString("flight_number"),
                        rs.getString("origin"), rs.getString("dest"),
                        rs.getTimestamp("departure_time").toLocalDateTime(), rs.getTimestamp("arrival_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateFlightStatus(int scheduleId, String status) {
        String sql = "UPDATE flight_schedules SET status = ? WHERE schedule_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, scheduleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}