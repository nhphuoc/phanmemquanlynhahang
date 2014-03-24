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
public class TbServer {
    private String ip;
    private int port;
    private String user;
    private String pass;

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
    public TbServer(String ip,int port,String u,String p){
        this.ip=ip;
        this.port=port;
        this.user=u;
        this.pass=p;
    }
    public static TbServer getValues() {
        TbServer tbserver;
        ConnectSQLite cn = new ConnectSQLite();
        Connection con = cn.connectSQLite();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbserver;");
            while (rs.next()) {
                tbserver = new TbServer(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                return tbserver;
            }
            rs.close();
            stmt.close();
            con.close();
         
        } catch (Exception e) {
        }finally{
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
