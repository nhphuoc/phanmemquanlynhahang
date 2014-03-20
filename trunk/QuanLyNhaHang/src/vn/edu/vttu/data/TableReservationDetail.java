/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nhphuoc
 */
public class TableReservationDetail {

    private int ID;
    private int ID_TABLE;
    private int ID_RESERVATION;

    public int getID() {
        return ID;
    }

    public int getID_TABLE() {
        return ID_TABLE;
    }

    public int getID_RESERVATION() {
        return ID_RESERVATION;
    }
    public TableReservationDetail(int id, int idtable, int idreservation){
        this.ID=id;
        this.ID_TABLE=idtable;
        this.ID_RESERVATION=idreservation;
    }
    public static boolean insert(int idTable, int idReservation, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_reservation_detail_insert(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idTable);
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

    public static boolean updateTable(int idReservation, int idTable, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_reservation_detail_update_table_by_idReservationDetail(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);
            callstate.setInt(2, idTable);

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

    public static boolean updateTable(int idReservation, int idTable, int idTableChange, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_reservation_detail_update_table_by_idTable_idReservation(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);
            callstate.setInt(2, idTable);
            callstate.setInt(3, idTableChange);

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

    public static boolean updateIdReservation(int idReservation1, int idReservation2, int idTable, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_reservation_detail_update_id_table_Reservation_id(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation1);
            callstate.setInt(2, idReservation2);
            callstate.setInt(3, idTable);

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

    public static TableReservationDetail[] getListTableByIdReservation(int idReservation, Connection conn) {
        TableReservationDetail table_list[] = null;
        try {
            String sql = "SELECT * FROM table_reservation_detail WHERE id_table_reservation_id="+idReservation;
            CallableStatement calState = conn.prepareCall(sql);            
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            table_list = new TableReservationDetail[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                table_list[i] = new TableReservationDetail(rs.getInt("id"),rs.getInt("id_table_id"),rs.getInt("id_table_reservation_id"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table_list;
    }
    public static boolean delete(int idReservation, int idTable,Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_reservation_detail_delete(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idTable);
            callstate.setInt(2, idReservation);

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

}
