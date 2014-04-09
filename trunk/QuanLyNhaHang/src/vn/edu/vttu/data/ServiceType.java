/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class ServiceType {
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
    public ServiceType(int id, String name){
        this.id=id;
        this.name=name;
    }
    public static ServiceType[] getAll(Connection conn){
        ServiceType[] svType = null;
        try {
            String sql = "call service_type_get_all()";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            svType = new ServiceType[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                svType[i] = new ServiceType(rs.getInt("id"),rs.getString("name"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return svType;
    }
    public static TableModel loadDataTable(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                       
            CallableStatement calState = conn.prepareCall("{CALL service_type_get_all()}");                        
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static Vector selectServiceType(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call service_type_get_all()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.ServiceType tb = new vn.edu.vttu.model.ServiceType(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static boolean insert(String name,Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL service_type_add(?)";
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
    public static boolean update(String name,int id,Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL service_type_update(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);                       
            callstate.setInt(2, id);                       
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
     public static boolean testName(String name,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        boolean t = false;
        try {                       
            CallableStatement calState = conn.prepareCall("{CALL service_type_test_name(?)}");                        
            calState.setString(1, name);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
            if(tb.getRowCount()>=1){
                t=false;
            }else{
                t=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    
}
