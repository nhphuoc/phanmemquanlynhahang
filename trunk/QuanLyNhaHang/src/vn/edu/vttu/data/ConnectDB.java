/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import config.InfoRestaurant;
import config.readxml;
import java.sql.Connection;
import java.sql.DriverManager;
import vn.edu.vttu.sqlite.TbConnection;
import vn.edu.vttu.ui.loadConnection;

/**
 *
 * @author nhphuoc
 */
public class ConnectDB {

    public static String db;
    public static String usr;
    public static String pass;
    public static String ipdb;
    public static int port;

    public static Connection conn() {
        Connection cn =null;        
        try {
            
            db = TbConnection.getValues().getDbname() + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";            
            usr = TbConnection.getValues().getUser();
            pass = TbConnection.getValues().getPass();
            ipdb = TbConnection.getValues().getIp();
            port = TbConnection.getValues().getPort();            
            
            Class.forName("com.mysql.jdbc.Driver");
            String database = "jdbc:mysql://" + ipdb + ":" + port + "/" + db;
            // String database = "jdbc:mysql://localhost:3306/vttu_restaurant";
            cn = DriverManager.getConnection(database, usr, pass);            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }
}
