/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author nhphuoc
 */
public class Tablereservationdetail {
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
    public static boolean insert(int idTable, int idReservation, Connection conn){
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
    
    
    
}
