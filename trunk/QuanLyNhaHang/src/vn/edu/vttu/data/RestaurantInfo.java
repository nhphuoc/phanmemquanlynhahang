/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author nhphuoc
 */
public class RestaurantInfo {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String logo;    
    private int hourReservationNomal;    
    private int hourReservationParty;    
    private int minuteWarning;    

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getHourReservationNomal() {
        return hourReservationNomal;
    }

    public void setHourReservationNomal(int hourReservationNomal) {
        this.hourReservationNomal = hourReservationNomal;
    }

    public int getHourReservationParty() {
        return hourReservationParty;
    }

    public void setHourReservationParty(int hourReservationParty) {
        this.hourReservationParty = hourReservationParty;
    }

    public int getMinuteWarning() {
        return minuteWarning;
    }

    public void setMinuteWarning(int minuteWarning) {
        this.minuteWarning = minuteWarning;
    }
    public RestaurantInfo(
            int id,
            String name,
            String phone,
            String addres,
            String email,
            String logo,
            int hourResercationNomal,
            int hourReservationParty,
            int minuteWarming
    ){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.address=addres;
        this.email=email;
        this.logo=logo;
        this.hourReservationNomal=hourResercationNomal;
        this.hourReservationParty=hourReservationParty;
        this.minuteWarning=minuteWarming;
    }
    public static RestaurantInfo getinfo(Connection conn) {
        RestaurantInfo restaurantinfo;
        try {
            String sql = "call restaurant_info_get_all()";
            CallableStatement callstate = conn.prepareCall(sql);            
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                restaurantinfo = new RestaurantInfo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)
                );
                return restaurantinfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean update(            
            String name,
            String phone,
            String addres,
            String email,
            String logo,
            int hourResercationNomal,
            int hourReservationParty,
            int minuteWarming,            
            Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL restaurant_info_update(?,?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);                            
            callstate.setString(1, name);                      
            callstate.setString(2, phone);                      
            callstate.setString(3, addres);                      
            callstate.setString(4, email);                      
            callstate.setString(5, logo);                      
            callstate.setInt(6, hourResercationNomal);                      
            callstate.setInt(7, hourReservationParty);                      
            callstate.setInt(8, minuteWarming);                      
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
