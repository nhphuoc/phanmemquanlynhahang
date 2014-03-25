/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhphuoc
 */
public class UpdateValues {

    public boolean updateRestaurant(String name, String address, String phone, String email, String logo,
            int hourNomal, int hourparty, int minute) {
        Connection c = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            ConnectSQLite cn = new ConnectSQLite();
            c = cn.connectSQLite();
            c.setAutoCommit(false);
            CreateTable reateTB = new CreateTable();
            reateTB.create();
            stmt = c.createStatement();
            String sql = "UPDATE tbrestaurant set NAME='" + name + "',ADDRESS='" + address + "',PHONE='" + phone + "',EMAIL='" + email + "',LOGO='" + logo + "',HOUR_RESERVATION_NOMAL='" + hourNomal + "',HOUR_RESERVATION_PARTY='" + hourparty + "',MINUTE_WARNING='" + minute + "'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }finally{
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(InsertValues.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return flag;
    }
    
    public boolean updateConnection(String db, String ip, int port, String u, String p) {
        Connection c = null;
        Statement stmt = null;
        boolean flag = false;
        try {
            ConnectSQLite cn = new ConnectSQLite();
            c = cn.connectSQLite();
            c.setAutoCommit(false);
            CreateTable reateTB = new CreateTable();
            reateTB.create();
            stmt = c.createStatement();
            String sql = "UPDATE tbconnection set NAME_DB='" + db + "',IP_DB='" + ip + "',PORT_DB='" + port + "',USER_DB='" + u + "',PASS_DB='" + p + "'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }finally{
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(InsertValues.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return flag;
    }

}
