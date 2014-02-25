/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Statement;

/**
 *
 * @author nhphuoc
 */
public class Invoice {
    private int id;
    private int id_reservation_id;
    private int id_staff;
    private String date;
    private int cost;
    private int discount;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_reservation_id() {
        return id_reservation_id;
    }

    public void setId_reservation_id(int id_reservation_id) {
        this.id_reservation_id = id_reservation_id;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public Invoice(int id, int id_reservation, int id_staff, String date, int cost, int discount, String note){
        this.id=id;
        this.id_reservation_id=id_reservation;
        this.id_staff=id_staff;
        this.date=date;
        this.cost=cost;
        this.discount=discount;
        this.note=note;
    }
    public static boolean insert(int id_reservation, int id_staff, int cost, int discount, String note) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL invoice_insert_all(?,?,?,?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, id_reservation);
            callstate.setInt(2, id_staff);
            callstate.setInt(3, cost);
            callstate.setInt(4, discount);
            callstate.setString(5, note);
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
