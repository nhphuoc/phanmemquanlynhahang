/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;

/**
 *
 * @author nhphuoc
 */
public class PasswordChange {
    private int id;
    private String pass;
    private Timestamp date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    public PasswordChange(int id, String pass, Timestamp date){
        this.id=id;
        this.pass=pass;
        this.date=date;
    }
    public static boolean insert(String pass,Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL password_change_add(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, pass);
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
