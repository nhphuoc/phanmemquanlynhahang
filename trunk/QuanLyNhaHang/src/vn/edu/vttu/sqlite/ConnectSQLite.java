/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.sqlite;

/**
 *
 * @author nhphuoc
 */
import java.sql.*;

public class ConnectSQLite {

    public Connection connectSQLite() {
        Connection c = null;        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:config.db");
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
        return c;
    }    
}
