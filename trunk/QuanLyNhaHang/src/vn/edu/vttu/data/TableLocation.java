/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author nhphuoc
 */
public class TableLocation {
    private int ID;
    private String NAME;

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
    public TableLocation (int ID, String NAME){
        this.ID=ID;
        this.NAME=NAME;
    }
    public static TableLocation[] getAll(Connection conn) {     
        TableLocation tables_location[] = null;
        try {
            String sql = "call table_location_getAll()";
            CallableStatement calState = conn.prepareCall(sql);
        ResultSet rs = calState.executeQuery(sql);
        rs.last();
        tables_location = new TableLocation[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            tables_location[i] = new TableLocation(rs.getInt("id"), rs.getString("name"));
            i++;
        }
        
        } catch (Exception e) {
        }
        return tables_location;
    }  
    public static TableLocation[] getTableById(Integer id,Connection conn) {     
        TableLocation tables_location[] = null;
        try {
            String sql = "SELECT * FROM customer WHERE customer.id="+id;
            CallableStatement calState = conn.prepareCall(sql);            
        ResultSet rs = calState.executeQuery(sql);
        rs.last();
        tables_location = new TableLocation[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            tables_location[i] = new TableLocation(rs.getInt("id"), rs.getString("name"));
            i++;
        }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables_location;
    }
    public static Vector selectTableLocation(Connection conn) {
        Vector result = new Vector();
        try {
            String sql = "call table_location_getAll()";
            CallableStatement callstate = conn.prepareCall(sql);
            ResultSet rs = callstate.executeQuery();
            while (rs.next()) {
                vn.edu.vttu.model.TableLocation tb = new vn.edu.vttu.model.TableLocation(rs.getInt(1), rs.getString(2));
                result.add(tb);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
    public static boolean insert(String name,String detail, Connection conn) {
        boolean flag = false;
        try {
            String sql = "CALL table_location_add(?,?)";
            CallableStatement callstate = conn.prepareCall(sql);
            callstate.setString(1, name);
            callstate.setString(2, detail);
            int x = callstate.executeUpdate();
            if (x ==1) {
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
