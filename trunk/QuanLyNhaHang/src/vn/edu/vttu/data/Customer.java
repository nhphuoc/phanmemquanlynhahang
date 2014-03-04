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
import java.util.Vector;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Customer {
    private static int ID;
    private String NAME;
    private boolean SEX;
    private Date BIRTHDAY;
    private String PHONE;
    private String ADDRESS;
    private String EMAIL;

    public static int getID() {
        return ID;
    }
    public static void setID(int ID) {
        Customer.ID = ID;
    }
    public String getNAME() {
        return NAME;
    }

    public boolean isSEX() {
        return SEX;
    }

    public Date getBIRTHDAY() {
        return BIRTHDAY;
    }

    public String getPHONE() {
        return PHONE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getEMAIL() {
        return EMAIL;
    }
    public static TableModel getLimit(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                     
            CallableStatement calState = conn.prepareCall("{CALL customer_get_limit()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static TableModel searchNamePhone(String key, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL customer_search_name_phone(?)}");            
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static Vector selectCustomer(Connection conn) {
        Vector result = new Vector();
        try {
            String sql="CALL customer_get_limit()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.Customer tb = new vn.edu.vttu.model.Customer(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    
}
