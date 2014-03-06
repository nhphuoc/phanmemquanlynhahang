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
    private String TYPE_NAME;
    private String UNIT;   
    private int STORE;
    private int COST;   
    private String DETAIL;
    private String IMAGES;

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
    public String getTYPE_NAME() {
        return TYPE_NAME;
    }
    public int getCOST() {
        return COST;
    }

    public void setCOST(int COST) {
        this.COST = COST;
    }
    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }
    public int getTYPE() {
        return TYPE;
    }
    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
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
    public Service(int id, String name, String type_name,int type, String unit, int store,String detail,String images,int cost){
        this.ID=id;
        this.NAME=name;
        this.TYPE_NAME=type_name;
        this.TYPE=type;
        this.STORE=store;
        this.DETAIL=detail;
        this.IMAGES=images;
        this.UNIT=unit;
        this.COST=cost;
    }
    public static Service getById(int id, Connection conn){
        Service sv;
        try {
            String sql = "call service_getByID(?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setInt(1, id);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                sv = new Service(rs.getInt("id"),rs.getString("name"),rs.getString("type_name"),rs.getInt("idType"),rs.getString("unit"),rs.getInt("store"),rs.getString("detail"),rs.getString("image"),rs.getInt("dongia"));
                return sv;
            }
        } catch (Exception e) {
        }
        return null;
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
    public static Service[] getAll(Connection conn) {
        Service[] sv = null;
        try {
            String sql = "call service_get_all()";
            CallableStatement calState = conn.prepareCall(sql);
            ResultSet rs = calState.executeQuery(sql);
            rs.last();
            sv = new Service[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                sv[i] = new Service(rs.getInt("id"),rs.getString("name"),rs.getString("type_name"),rs.getInt("idType"),rs.getString("unit"),rs.getInt("store"),rs.getString("detail"),rs.getString("image"),rs.getInt("dongia"));
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sv;
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
    public static TableModel searchGetAll(String key,Connection conn) {
        TableModel tb = null;
        ResultSet rs;
        try {                    
            CallableStatement calState = conn.prepareCall("{CALL service_search_get_all(?)}");            
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
