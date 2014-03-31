/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class ClientRequest {

    private int id;
    private int id_request;
    private int id_table;
    private boolean status;
    private Timestamp date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_request() {
        return id_request;
    }

    public void setId_request(int id_request) {
        this.id_request = id_request;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public ClientRequest(int id, int id_request) {
        this.id = id;
        this.id_request = id_request;
    }

    public ClientRequest(int id, int id_request, int id_table, boolean status, Timestamp date) {
        this.id = id;
        this.id_request = id_request;
        this.id_table = id_table;
        this.status = status;
        this.date = date;
    }

    public static ClientRequest getMaxRow(Connection conn) {
        ClientRequest request = null;
        ResultSet rs = null;
        try {
            String sql = "call client_request_get_max_id()";
            CallableStatement calState = conn.prepareCall(sql);
            rs = calState.executeQuery(sql);
            while (rs.next()) {
                request = new ClientRequest(rs.getInt("id"), rs.getInt("id_request_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                conn.close();
            } catch (Exception e) {
            }

        }
        return request;

    }

    public static ClientRequest getById(int id, Connection conn) {
        ClientRequest request = null;
        try {
            String sql = "call client_request_get_by_id(?)";
            CallableStatement calState = conn.prepareCall(sql);
            calState.setInt(1, id);
            ResultSet rs = calState.executeQuery();
            while (rs.next()) {
                request = new ClientRequest(rs.getInt("id"), rs.getInt("id_request_id"), rs.getInt("id_table_id"), rs.getBoolean("status"), rs.getTimestamp("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;

    }

    public static TableModel getAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL client_request_getAll()}");
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static boolean accept(int id, boolean accept, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL client_request_accept(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setBoolean(2, accept);
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
