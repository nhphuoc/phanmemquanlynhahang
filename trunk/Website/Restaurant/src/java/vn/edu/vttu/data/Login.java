/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nhphuoc
 */
public class Login {
    public boolean checklogin(String user, String pass) {
        boolean flag = false;        
        try {
            String db = "jsp";
            String usr = "root";
           
            String ip = "localhost";
            int port = 3306;
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql:3306//localhost/jsp";
            Connection cn = DriverManager.getConnection(database, usr, pass);
            flag=true;            
        } catch (Exception e) {
            flag=false;
        }
        return flag;

    }

}
