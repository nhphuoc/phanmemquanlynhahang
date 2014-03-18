package vn.edu.vttu.sqlite;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertValues {

    public boolean insertConnectionInfo(String db, String ip, int port, String u, String p) {
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
            String sql = "INSERT INTO tbconnection(NAME_DB,IP_DB,PORT_DB,USER_DB,PASS_DB) "
                    + "VALUES('" + db + "','" + ip + "','" + port + "','" + u + "','" + p + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            stmt.close();
            c.close();
            flag = true;
        } catch (Exception e) {
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

    public boolean insertServerInfo(String ip, int port, String u, String p) {
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
            String sql = "INSERT INTO tbserver(IP_SRV,PORT_SRV,USER_SRV,PASS_SRV) "
                    + "VALUES('" + ip + "','" + port + "','" + u + "','" + p + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            stmt.close();
            c.close();
            flag = true;
        } catch (Exception e) {
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

    public boolean insertRestaurant(String name, String addr, String phone, String email, String logo,
            int hourNomal, int hourParty, int minute) {
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
            String sql = "INSERT INTO tbrestaurant(NAME,ADDRESS,PHONE,EMAIL,LOGO,HOUR_RESERVATION_NOMAL,HOUR_RESERVATION_PARTY,MINUTE_WARNING) "
                    + "VALUES('" + name + "','" + addr + "','" + phone + "','" + email + "','" + logo + "','" + hourNomal + "','" + hourParty + "','" + minute + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            stmt.close();
            c.close();
            flag = true;
        } catch (Exception e) {
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
