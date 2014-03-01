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
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class TableReservation {

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
        TableReservation.DateReservation = DateReservation;
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

    public TableReservation(int id) {
        this.ID = id;
    }

    public TableReservation(int id, int customer_id, String customer_name, String beginDate) {
        this.ID=id;
        this.CUSTOMER=customer_id;
        this.CUSTOMER_NAME=customer_name;
        this.beginDate=beginDate;
    }

    public static TableReservation getMaxID(Connection conn) {
        try {            
            Statement state = conn.createStatement();
            String sql = "call table_reservation_getMaxID()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            TableReservation table_reservation;
            while (rs.next()) {
                table_reservation = new TableReservation(rs.getInt("id"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TableReservation getMaxIDReservation(int idTable, Connection conn) {
        try {            
            String sql = "call table_reservation_getmaxid_by_Reservation(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1,idTable);
            ResultSet rs = callstate.executeQuery();
            TableReservation table_reservation;
            while (rs.next()) {
                table_reservation = new TableReservation(rs.getInt("id"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static TableReservation getByTableByStatus(int idTable, Connection conn) {
       TableReservation table_reservation;
        try {            
            String sql = "call table_reservation_getby_IdTable(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idTable);            
            ResultSet rs = callstate.executeQuery();
            
            while (rs.next()) {
                table_reservation = new TableReservation(rs.getInt("id"),rs.getInt("id_customer_id"),rs.getString("name"),rs.getString("beginDate"));
                return table_reservation;
            }
        } catch (Exception e) {
        }
        table_reservation = new TableReservation(-1,-1,"Chọn khách hàng","Chưa sử dụng");
        return table_reservation;
    }
    public static boolean insert(boolean status, Connection conn) {
        boolean flag = false;
        try {            
            Statement state = conn.createStatement();
            String sql = "CALL table_reservation_insert(?)";            
            CallableStatement callstate = conn.prepareCall(sql);
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
    
    public static boolean insert(boolean status, int idCustomer, String dt, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_insert_customerid(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
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
    
    
    public static boolean updateCustomer(int idCustomer, int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_update_customer(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
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
    
    public static boolean updateStatus(int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_update_status(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);            
            int x = callstate.executeUpdate();
            if (x >=0) {
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
    public static boolean updateEndDate(int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_update_enddate(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);            
            int x = callstate.executeUpdate();
            if (x >=0) {
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
    public static boolean updateBeginDate(int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_update_begindate(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);            
            int x = callstate.executeUpdate();
            if (x >=0) {
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
    public static boolean updateBeginDate(int idReservation,String dt, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_update_begindate_date(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);            
            callstate.setString(2, dt);            
            int x = callstate.executeUpdate();
            if (x >= 0) {
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
    
    public static int countidTableReservation(int idTable, Connection conn) {
        int count=0;
        ResultSet rs;
        try {                        
            CallableStatement calState = conn.prepareCall("{CALL table_reservation_select_list_table_reservation(?)}");            
            calState.setInt(1, idTable);
            rs = calState.executeQuery();            
            while(rs.next()){
                count=count+1;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public static int countidTableUsing(int idTable, Connection conn) {
        int count=0;
        ResultSet rs;
        try {                        
            CallableStatement calState = conn.prepareCall("{CALL table_reservation_count_table_using(?)}");            
            calState.setInt(1, idTable);
            rs = calState.executeQuery();            
            while(rs.next()){
                count=count+1;
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public static TableModel getListTableReservation(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL table_reservation_view_list()}");                        
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    
    public static TableModel getListTableReservationSearch(String key,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL table_reservation_view_list_search(?)}");                        
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean reservationCancel(int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_reservation_cancel(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);            
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
