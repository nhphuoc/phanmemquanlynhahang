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
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Unit {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TableModel getAll(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {
            CallableStatement calState = conn.prepareCall("{CALL unit_getAll()}");
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }

    public static boolean insert(String name, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL unit_add(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
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

    public static boolean update(String name, int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL unit_update(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setInt(2, id);
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
    public static boolean delete(int id, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL unit_del(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);            
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
    public static boolean testName(String name,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean flag=false;
        try {
            CallableStatement calState = conn.prepareCall("{CALL unit_test_name(?)}");
            calState.setString(1, name);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()>0){
                flag=false;
            }else{
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    public static Vector selectUnit(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call unit_getAll()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.Unit tb = new vn.edu.vttu.model.Unit(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
