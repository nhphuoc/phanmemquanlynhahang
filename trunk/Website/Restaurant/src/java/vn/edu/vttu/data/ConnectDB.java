/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author nhphuoc
 */
public class ConnectDB {
    public  Connection conn() {
        Connection cn = null;
        //readInfo read = new readInfo();
        //Server sv = new Server();
        try {
            String db = "jsp";
            String usr = "root";
            String pass = "";
            String ip = "localhost";
            int port = 3306;
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            cn = DriverManager.getConnection(database, usr, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }
}
