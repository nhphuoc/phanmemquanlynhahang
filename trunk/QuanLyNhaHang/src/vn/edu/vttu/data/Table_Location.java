/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.edu.vttu.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nhphuoc
 */
public class Table_Location {
    private int ID;
    private String NAME;

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
    public Table_Location(int ID, String NAME){
        this.ID=ID;
        this.NAME=NAME;
    }
    public static Table_Location[] getAll() throws SQLException {

        Statement state = connectDB.conn().createStatement();
        String sql = "call table_location_getAll()";
        CallableStatement calState = connectDB.conn().prepareCall(sql);
        ResultSet rs = calState.executeQuery(sql);
        rs.last();
        Table_Location[] tables_location = new Table_Location[rs.getRow()];
        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            tables_location[i] = new Table_Location(rs.getInt("id"), rs.getString("name"));
            i++;
        }
        return tables_location;
    }
    
    
}
