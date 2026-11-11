/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import Config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class DBConnection {

    public static Connection getConnection() throws SQLException {
        String url = Config.get("db.url");
        String user = Config.get("db.user");
        String pass = Config.get("db.pass");
        return DriverManager.getConnection(url, user, pass);
    }
}
