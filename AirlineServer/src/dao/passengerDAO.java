/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DB.DBConnection;
import model.passenger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Passenger Data Access Object for server-side database operations.
 */
public class passengerDAO {

    public int addPassenger(passenger p) {
        String sql = "INSERT INTO passengers (first_name, last_name, gender, date_of_birth, nationality, passport_number, email, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, p.getFirstName());
            pst.setString(2, p.getLastName());
            pst.setString(3, p.getGender());
            pst.setDate(4, java.sql.Date.valueOf(p.getDateOfBirth()));
            pst.setString(5, p.getNationality());
            pst.setString(6, p.getPassportNumber());
            pst.setString(7, p.getEmail());
            pst.setString(8, p.getPhoneNumber());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deletePassenger(int passengerId) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passengerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<passenger> getAllPassengers() {
        List<passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new passenger(
                        rs.getInt("passenger_id"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("gender"), rs.getDate("date_of_birth").toLocalDate(), rs.getString("nationality"),
                        rs.getString("passport_number"), rs.getString("email"), rs.getString("phone_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<passenger> findPassengersByPassport(String passportQuery) {
        List<passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers WHERE UPPER(passport_number) LIKE UPPER(?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, "%" + passportQuery + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new passenger(
                            rs.getInt("passenger_id"), rs.getString("first_name"), rs.getString("last_name"),
                            rs.getString("gender"), rs.getDate("date_of_birth").toLocalDate(), rs.getString("nationality"),
                            rs.getString("passport_number"), rs.getString("email"), rs.getString("phone_number")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}