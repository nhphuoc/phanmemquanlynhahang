package vn.edu.vttu.sqlite;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateTable {

    public boolean create() {
        Connection c = null;
        Statement stmt = null;
        boolean f = false;
        try {
            ConnectSQLite cn = new ConnectSQLite();
            c = cn.connectSQLite();
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tbconnection("
                    + " NAME_DB   TEXT, "
                    + " IP_DB     TEXT, "
                    + " PORT_DB   INT, "
                    + " USER_DB   TEXT,"
                    + " PASS_DB   TEXT)";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS tbserver( "
                    + " IP_SRV     TEXT, "
                    + " PORT_SRV   INT, "
                    + " USER_SRV   TEXT,"
                    + " PASS_SRV   TEXT)";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS tbrestaurant( "
                    + " NAME     TEXT, "
                    + " ADDRESS  TEXT, "
                    + " PHONE   TEXT,"
                    + " EMAIL   TEXT,"
                    + " LOGO    TEXT,"
                    + " HOUR_RESERVATION_NOMAL INT default 2,"
                    + " HOUR_RESERVATION_PARTY INT default 5,"
                    + " MINUTE_WARNING INT default 2"
                    + ")";

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            f = true;
            stmt.close();
        } catch (Exception e) {
            f = false;
        } finally {
            try {
                stmt.close();
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(CreateTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return f;
    }
}
