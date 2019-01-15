/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jasve
 */
public class GetConnection {

    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/PCsAppointments", "ProjectOneTeam", "123456");
    }

}
