/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nhphuoc
 */
public class Login {

    public boolean  checklogin(String user, String pass) {
        boolean flag=false;
        try {
            
            Connection conn;
            conn=(Connection) new ConnectDB();
            String sql = "select * from usr where username=" + user + " and pass=" + pass;
            PreparedStatement stm = conn.prepareStatement(sql);            
            ResultSet rs = stm.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
            }
            if(i==1){
                flag=true;
            }else{
                flag=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

}
