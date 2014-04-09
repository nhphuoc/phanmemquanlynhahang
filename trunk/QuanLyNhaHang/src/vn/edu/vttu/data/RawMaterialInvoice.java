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
public class RawMaterialInvoice {
    private int id;
    private Timestamp date;
    private int id_staff;
    private int id_isdistributor;

    public Timestamp getDate() {
        return date;
    }

    public int getId_staff() {
        return id_staff;
    }

    public int getId_isdistributor() {
        return id_isdistributor;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public RawMaterialInvoice(int id){
        this.id=id;
    }
    public RawMaterialInvoice(int id,int id_distributor){
        this.id=id;
        this.id_isdistributor=id_distributor;
    }
    public static TableModel getByDate(Timestamp fromdate, Timestamp todate,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_invoice_get_by_date(?,?)}");
            calState.setTimestamp(1, fromdate);
            calState.setTimestamp(2, todate);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean insert(int staff,int distributor,String note, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_invoice_add(?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);            
            callstate.setInt(1, staff);
            callstate.setInt(2, distributor);
            callstate.setString(3, note);
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
    public static RawMaterialInvoice getMaxID(Connection conn) {
        RawMaterialInvoice rawinvoice;
        try {
            String sql = "call raw_material_invoice_get_max_id()";
            CallableStatement callstate = conn.prepareCall(sql);            
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                rawinvoice = new RawMaterialInvoice(rs.getInt("id"));
                return rawinvoice;
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static RawMaterialInvoice getDistributor(Connection conn) {
        RawMaterialInvoice rawinvoice;
        try {
            String sql = "call raw_material_invoice_get_id_distributor()";
            CallableStatement callstate = conn.prepareCall(sql);            
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                rawinvoice = new RawMaterialInvoice(rs.getInt("id"),rs.getInt("id_istributor_id"));
                return rawinvoice;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
}
