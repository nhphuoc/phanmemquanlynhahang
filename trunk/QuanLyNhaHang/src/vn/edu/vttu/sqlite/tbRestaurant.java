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
public class tbRestaurant {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String logo;

    private int hourReservationNomal;
    private int hourReservationParty;
    private int minuteWarning;

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

    public tbRestaurant(String name, String addr, String phone, String email, String logo,
            int hourReservationNomal, int hourReservationParty, int minuteWarning) {
        this.name = name;
        this.address = addr;
        this.phone = phone;
        this.email = email;
        this.logo = logo;
        this.hourReservationNomal = hourReservationNomal;
        this.hourReservationParty = hourReservationParty;
        this.minuteWarning = minuteWarning;
    }

    public static tbRestaurant getValues() {
        tbRestaurant tbrestaurant;
        ConnectSQLite cn = new ConnectSQLite();
        Connection con = cn.connectSQLite();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbrestaurant;");
            while (rs.next()) {
                tbrestaurant = new tbRestaurant(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
                return tbrestaurant;
            }
            rs.close();
            stmt.close();
            con.close();

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
