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
public class Tablelocation {
    private int ID;
    private String NAME;

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
    public Tablelocation (int ID, String NAME){
        this.ID=ID;
        this.NAME=NAME;
    }
    public static Tablelocation[] getAll(Connection conn) throws SQLException {        
        String sql = "call table_location_getAll()";
        CallableStatement calState = conn.prepareCall(sql);
        ResultSet rs = calState.executeQuery(sql);
        rs.last();
        Tablelocation tables_location[] = new Tablelocation[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            tables_location[i] = new Tablelocation(rs.getInt("id"), rs.getString("name"));
            i++;
        }
        return tables_location;
    }  
    
    
}
