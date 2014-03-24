/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

/**
 *
 * @author nhphuoc
 */
public class Distributor {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Distributor(int id, String name,String address,String phone,String email){
        this.id=id;
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.email=email;
    }
    public static Vector selectDistributor(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call distributor_get_all()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.Distributor tb = new vn.edu.vttu.model.Distributor(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    public static Distributor getByID(int id, Connection conn) {
        Distributor distributor;
        try {
            String sql = "call distributor_get_by_id(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                distributor = new Distributor(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("phone"), rs.getString("email"));
                return distributor;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static boolean insertName(String name, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL distributor_add_name(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);            
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
