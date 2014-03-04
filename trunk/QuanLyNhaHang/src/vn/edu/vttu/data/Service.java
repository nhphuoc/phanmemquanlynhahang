/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author nhphuoc
 */
public class Service {
    private int ID;
    private String NAME;
    private int TYPE;
    private int STORE;
    private String DETAIL;
    private String IMAGES;

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public int getTYPE() {
        return TYPE;
    }

    public int getSTORE() {
        return STORE;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public String getIMAGES() {
        return IMAGES;
    }
    public static TableModel getAllLimit(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL service_get_limit()}");            
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
            CallableStatement calState = conn.prepareCall("{CALL service_get_all()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static TableModel getMaxId(Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL service_get_max_id()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static TableModel searchByName(String key, Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                       
            CallableStatement calState = conn.prepareCall("{CALL service_search_byName(?)}");            
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean updateName(String name, int idService, Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL service_update_name(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setInt(2, idService);            
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
    public static boolean insert(String name, int type, String unit, int store, String detail, String img,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL service_add(?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);                  
            callstate.setInt(2, type);                  
            callstate.setString(3, unit);                  
            callstate.setInt(4, store);                  
            callstate.setString(5, detail);                  
            callstate.setString(6, img);                  
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
    public static boolean update(int id,String name, int type, String unit, int store, String detail, String img,  Connection conn) {
        boolean flag = false;
        try {            
            String sql = "CALL service_update_by_id(?,?,?,?,?,?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            callstate.setString(2, name);                  
            callstate.setInt(3, type);                  
            callstate.setString(4, unit);                  
            callstate.setInt(5, store);                  
            callstate.setString(6, detail);                  
            callstate.setString(7, img);                  
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
