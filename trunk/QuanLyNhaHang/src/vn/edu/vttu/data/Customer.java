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

    public static TableModel searchGetAll(String key, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL customer_search_get_all(?)}");
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static TableModel getAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL customer_get_all()}");
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
            String sql = "CALL customer_get_limit()";
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

    public static boolean insert(String name, Boolean sex, Date birthday, String phone, String address, String email, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL customer_add(?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setBoolean(2, sex);
            callstate.setDate(3, birthday);
            callstate.setString(4, phone);
            callstate.setString(5, address);
            callstate.setString(6, email);
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

    public static boolean updateAll(int id, String name, Boolean sex, Date birthday, String phone, String address, String email, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL customer_update_all(?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setString(2, name);
            callstate.setBoolean(3, sex);
            callstate.setDate(4, birthday);
            callstate.setString(5, phone);
            callstate.setString(6, address);
            callstate.setString(7, email);
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
