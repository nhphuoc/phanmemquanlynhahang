/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
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
    public static TableModel getAllService() {
        TableModel tb = null;
        ResultSet rs;
        try {
            Statement state = connectDB.conn().createStatement();            
            CallableStatement calState = connectDB.conn().prepareCall("{CALL service_getAll()}");            
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    
    public static TableModel searchByName(String key) {
        TableModel tb = null;
        ResultSet rs;
        try {
            Statement state = connectDB.conn().createStatement();            
            CallableStatement calState = connectDB.conn().prepareCall("{CALL service_search_byName(?)}");            
            calState.setString(1, key);
            rs = calState.executeQuery();
            tb = DbUtils.resultSetToTableModel(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tb;
    }
    public static boolean updateName(String name, int idService) {
        boolean flag = false;
        try {
            Statement state = connectDB.conn().createStatement();
            String sql = "CALL service_update_name(?,?)";
            CallableStatement callstate = connectDB.conn().prepareCall(sql);
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
    
}
