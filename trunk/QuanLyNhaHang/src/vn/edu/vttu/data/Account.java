/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;

/**
 *
 * @author nhphuoc
 */
public class Account {
    private int id;
    private int id_staff;
    private String user;
    private String pass;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public static boolean insert(int id_staff, String user,String pass,int type, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL account_add(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1,id_staff);
            callstate.setString(2,user);
            callstate.setString(3,pass);
            callstate.setInt(4,type);
            
            int x = callstate.executeUpdate();
            if (x == 1) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        return flag;
    }
    
}
