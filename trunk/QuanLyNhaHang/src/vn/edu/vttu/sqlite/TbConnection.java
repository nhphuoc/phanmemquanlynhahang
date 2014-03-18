/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhphuoc
 */
public class TbConnection {

    private String dbname;
    private String ip;
    private int port;
    private String user;
    private String pass;

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public TbConnection(String name, String ip, int port, String user, String pass) {
        this.dbname = name;
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }

    public static TbConnection getValues() {
        TbConnection tbconn;
        ConnectSQLite cn = new ConnectSQLite();
        Connection con = cn.connectSQLite();
        Statement stmt = null;
        CreateTable cr = new CreateTable();
        try {
            cr.create();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbconnection;");
            while (rs.next()) {
                tbconn = new TbConnection(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                return tbconn;
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(InsertValues.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
