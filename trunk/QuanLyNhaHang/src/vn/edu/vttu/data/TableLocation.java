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
    
    
}
