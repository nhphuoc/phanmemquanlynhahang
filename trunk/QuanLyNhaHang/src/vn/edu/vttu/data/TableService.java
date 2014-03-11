/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class TableService {

    private int ID;
    private int ID_SERVICE;
    private String NAME_SERVICE;
    private int NUMBER;
    private int COST;
    private int TOTAL;

    public int getID() {
        return ID;
    }

    public int getID_SERVICE() {
        return ID_SERVICE;
    }

    public String getNAME_SERVICE() {
        return NAME_SERVICE;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public int getCOST() {
        return COST;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public static TableModel getByIdReservation(int id, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {            
            CallableStatement calState = conn.prepareCall("{CALL table_service_getByIdReservation(?)}");
            calState.setInt(1, id);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static boolean insert(int id_reservation, int idService, int num, int cost, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_service_insert(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_reservation);
            callstate.setInt(2, idService);
            callstate.setInt(3, num);
            callstate.setInt(4, cost);
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

    public static boolean getServiceByIdService_ByIdReservation(int idService, int idReservation, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        String count;
        boolean flag = false;
        try {            
            CallableStatement calState = conn.prepareCall("{CALL table_service_count_service_byIdReservation(?,?)}");
            calState.setInt(1, idService);
            calState.setInt(2, idReservation);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            count = String.valueOf(tb.getValueAt(0, 0));
            if (count.equals("0")) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean update(int id_reservation, int idService, int num, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_service_update(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idService);
            callstate.setInt(2, num);
            callstate.setInt(3, id_reservation);
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

    public static boolean update(int idTableService, int num, Connection conn) throws SQLException {
        boolean flag = false;               
            String sql = "CALL table_service_update_number_byidTableService(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idTableService);
            callstate.setInt(2, num);
            int x = callstate.executeUpdate();
            if (x >=0) {
                flag = true;
            } else {
                flag = false;
            }        

        return flag;
    }

    public static boolean updateStstus(int idReservation, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_service_update_status_byIdReservation(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation);
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

    public static boolean delete(int idTableService, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_service_delete(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idTableService);
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

    public static int totalPayment(int idReservation, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        int total;
        try {            
            CallableStatement calState = conn.prepareCall("{CALL table_service_total_payment(?)}");
            calState.setInt(1, idReservation);
            rs = calState.executeQuery();
            rs.last();
            if (rs.getString("total") != null) {
                total = rs.getInt("total");
            } else {
                total = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            total = 0;
        }
        return total;
    }
    public static boolean updateIdReservation(int idReservation1, int idReservation2,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL table_service_update_id_reservation_id(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, idReservation1);
            callstate.setInt(2, idReservation2);
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
