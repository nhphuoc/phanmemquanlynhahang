/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nhphuoc
 */
public class Tablereservation {

    private int ID;
    private int CUSTOMER;
    private String CUSTOMER_NAME;
    private String beginDate;
    private String endDate;
    private boolean STATUS;
    
    private static String DateReservation;// use reservation

    public static String getDateReservation() {
        return DateReservation;
    }

    public static void setDateReservation(String DateReservation) {
        Tablereservation.DateReservation = DateReservation;
    }

    public int getID() {
        return ID;
    }

    public int getCUSTOMER() {
        return CUSTOMER;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isSTATUS() {
        return STATUS;
    }

    public Tablereservation(int id) {
        this.ID = id;
    }

    public Tablereservation(int id, int customer_id, String customer_name, String beginDate) {
        this.ID=id;
        this.CUSTOMER=customer_id;
        this.CUSTOMER_NAME=customer_name;
        this.beginDate=beginDate;
    }

    public static Tablereservation getMaxID() {
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "call table_reservation_getMaxID()";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            Tablereservation table_reservation;
            while (rs.next()) {
                table_reservation = new Tablereservation(rs.getInt("id"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static Tablereservation getMaxIDReservation(int idTable) {
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "call table_reservation_getmaxid_by_Reservation(?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1,idTable);
            ResultSet rs = callstate.executeQuery();
            Tablereservation table_reservation;
            while (rs.next()) {
                table_reservation = new Tablereservation(rs.getInt("id"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static Tablereservation getByTableByStatus(int idTable, int st) {
       Tablereservation table_reservation;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "call table_reservation_getIdTable_status(?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, idTable);
            callstate.setInt(2, st);
            ResultSet rs = callstate.executeQuery();
            
            while (rs.next()) {
                table_reservation = new Tablereservation(rs.getInt("id"),rs.getInt("id_customer_id"),rs.getString("name"),rs.getString("beginDate"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        table_reservation = new Tablereservation(-1,-1,"Chọn khách hàng","Chưa sử dụng");
        return table_reservation;
    }
    public static boolean insert(boolean status) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL table_reservation_insert(?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setBoolean(1, status);
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
    
    public static boolean insert(boolean status, int idCustomer, String dt) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL table_reservation_insert_customerid(?,?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setBoolean(1, status);
            callstate.setInt(2, idCustomer);
            callstate.setString(3, dt);
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
    
    
    public static boolean updateCustomer(int idCustomer, int idReservation) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL table_reservation_update_customer(?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, idCustomer);
            callstate.setInt(2, idReservation);
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
    
    public static boolean updateStatus(int idTable) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL table_reservation_update_status(?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
            callstate.setInt(1, idTable);            
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
