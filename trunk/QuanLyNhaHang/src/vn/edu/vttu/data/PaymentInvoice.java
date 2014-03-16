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
import java.sql.Timestamp;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class PaymentInvoice {
    public static TableModel getByDate(Timestamp dtFrom, Timestamp dtTo, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL payment_invoice_get_by_date(?,?)}");
            calState.setTimestamp(1, dtFrom);
            calState.setTimestamp(2, dtTo);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean insert(Timestamp dt,int id,int cost,String info,String note, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL payment_invoice_add_invoice(?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setTimestamp(1, dt);
            callstate.setInt(2, id);
            callstate.setInt(3, cost);
            callstate.setString(4, info);
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
