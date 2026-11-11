/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import DB.DBConnection;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object for server-side database operations.
 */
public class UserDAO {

    public int addUser(User user) {
        String sql = "INSERT INTO users (username, full_name, password, role) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getFullName());
            pst.setString(3, user.getPassword()); // TODO: hash password in production
            pst.setString(4, user.getRole());

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

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findUsers(String usernameQuery) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE username LIKE ? OR full_name LIKE ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            String query = "%" + usernameQuery + "%";
            pst.setString(1, query);
            pst.setString(2, query);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            rs.getString("password"),
                            rs.getString("role")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY user_id";
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateUser(int userId, String password, String fullName, String role) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        List<String> params = new ArrayList<>();
        
        if (password != null && !password.isEmpty()) {
            params.add("password = ?");
        }
        if (fullName != null && !fullName.isEmpty()) {
            params.add("full_name = ?");
        }
        if (role != null && !role.isEmpty()) {
            params.add("role = ?");
        }
        
        if (params.isEmpty()) {
            return false; // Nothing to update
        }
        
        sql.append(String.join(", ", params));
        sql.append(" WHERE user_id = ?");
        
        try (Connection con = DBConnection.getConnection(); PreparedStatement pst = con.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (password != null && !password.isEmpty()) {
                pst.setString(paramIndex++, password); // TODO: hash password in production
            }
            if (fullName != null && !fullName.isEmpty()) {
                pst.setString(paramIndex++, fullName);
            }
            if (role != null && !role.isEmpty()) {
                pst.setString(paramIndex++, role);
            }
            pst.setInt(paramIndex, userId);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}