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
public class RawMaterialInvoiceDetail {
    public static TableModel getByIdRawmaterial(int id,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL raw_material_invoice_detail_get_by_id_raw_material_invoice(?)}");
            calState.setInt(1, id);            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean insert(int id_raw, int id_raw_invoice,float number,int cost, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL raw_material_invoice_detail_add(?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id_raw);            
            callstate.setInt(2, id_raw_invoice);            
            callstate.setFloat(3, number);            
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
    
}
